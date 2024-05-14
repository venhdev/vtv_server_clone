package hcmute.kltn.vtv.service.shipping.impl;

import hcmute.kltn.vtv.model.data.shipping.request.CashOrdersRequest;
import hcmute.kltn.vtv.model.data.shipping.response.CashOrdersByDatesResponse;
import hcmute.kltn.vtv.model.data.shipping.response.CashOrdersResponse;
import hcmute.kltn.vtv.model.entity.shipping.CashOrder;
import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.model.extra.TypeWork;
import hcmute.kltn.vtv.repository.shipping.CashOrderRepository;
import hcmute.kltn.vtv.repository.shipping.DeliverRepository;
import hcmute.kltn.vtv.repository.shipping.TransportRepository;
import hcmute.kltn.vtv.repository.user.OrderRepository;
import hcmute.kltn.vtv.service.shipping.ICashOrderService;
import hcmute.kltn.vtv.service.shipping.IDeliverService;
import hcmute.kltn.vtv.service.wallet.IWalletService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CashOrderServiceImpl implements ICashOrderService {

    private final CashOrderRepository cashOrderRepository;
    private final TransportRepository transportRepository;
    private final OrderRepository orderRepository;
    private final IWalletService walletService;
    private final DeliverRepository deliverRepository;
    private final IDeliverService deliverService;


    @Async
    @Override
    @Transactional
    public void addNewCashOrder(UUID transportId, String shipperUsername, boolean shipperHold, Status status) {
        cashOrderRepository.findByTransportId(transportId)
                .ifPresentOrElse(
                        cashOrder -> updateCashOrderByShipperHold(cashOrder, shipperHold),
                        () -> saveCashOrder(createCashOrderByTransportId(transportId, shipperUsername, shipperHold, status))
                );
    }


    @Override
    @Transactional
    public void updateCashOrderWithWaveHouseHold(UUID transportId, boolean shipperHold, String waveHouseUsername,
                                                 boolean waveHouseHold) {
        CashOrder cashOrder = getCashOrderByTransportId(transportId);
        cashOrder.setShipperHold(shipperHold);
        if (!cashOrder.getWaveHouseUsername().isEmpty() && !cashOrder.getWaveHouseUsername().equals(waveHouseUsername)) {
            throw new BadRequestException("Tài khoản kho không trùng khớp với tài khoản kho đã chọn!");
        }
        cashOrder.setWaveHouseUsername(waveHouseUsername);
        cashOrder.setWaveHouseHold(waveHouseHold);
        cashOrder.setUpdateAt(LocalDateTime.now());
        try {
            boolean handlePayment = false;
            if (!shipperHold && !waveHouseHold && cashOrder.getMoney() > 0L &&
                    checkStatusOrderByOrderId(cashOrder.getOrderId())) {
                walletService.updateWalletByOrderId(cashOrder.getOrderId(), "COMPLETED_ORDER_COD");
                handlePayment = true;
            }
            cashOrder.setHandlePayment(handlePayment);

            cashOrderRepository.save(cashOrder);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi cập nhật đơn thu tiền theo kho!");
        }
    }


    @Async
    @Override
    @Transactional
    public void updateCashOrderByOrderIdWithHandlePayment(UUID orderId) {
        if (checkCashOrderByOrderIdWithHandlePayment(orderId, false, false, true)) {
            throw new BadRequestException("Đơn thu tiền đã thanh toán cho đơn hàng này!");
        }

        try {
            CashOrder cashOrder = cashOrderRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền theo mã đơn hàng!"));
            cashOrder.setWaveHouseHold(false);
            cashOrder.setHandlePayment(true);
            cashOrder.setUpdateAt(LocalDateTime.now());

            cashOrderRepository.save(cashOrder);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi cập nhật đơn thu tiền theo kho!");
        }
    }


    @Override
    @Transactional
    public CashOrdersResponse updateCashOrdersWithWaveHouseHold(CashOrdersRequest cashOrdersRequest, String username, boolean shipperHold,
                                                                boolean waveHouseHold) {

        checkExistCashOrdersByCashOrderIds(cashOrdersRequest.getCashOrderIds(), username, waveHouseHold);
        checkExistWaveHouseByUsername(cashOrdersRequest.getWaveHouseUsername());
        try {
            List<CashOrder> cashOrders = getCashOrdersByCashOrderIds(cashOrdersRequest.getCashOrderIds());
            cashOrders.forEach(cashOrder -> updateCashOrderWithWaveHouseHold(
                    cashOrder.getTransportId(), shipperHold, cashOrdersRequest.getWaveHouseUsername(), waveHouseHold));

            String message = generateMessage(shipperHold, waveHouseHold);
            return CashOrdersResponse.cashOrdersResponse(cashOrders, message, "Success");
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi cập nhật đơn thu tiền theo kho!");
        }
    }


    @Override
    public CashOrdersResponse getListCashOrdersByShipperUsername(String shipperUsername) {
        deliverService.checkExistByTypeWorkShipperByUsername(shipperUsername);
        System.out.println("shipperUsername: " + shipperUsername);
        List<CashOrder> cashOrders = cashOrderRepository.findAllByShipperUsername(shipperUsername)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền theo tài khoản shipper!"));
        return CashOrdersResponse.cashOrdersResponse(cashOrders, "Lấy danh sách đơn thu tiền theo tài khoản shipper thành công!", "OK");
    }


    @Override
    public CashOrdersResponse getListCashOrdersByWaveHouseUsernameAnhStatus(String waveHouseUsername) {
        deliverService.checkExistByTypeWorkWarehouseByUsername(waveHouseUsername);
        List<CashOrder> cashOrders = cashOrderRepository.findAllByWaveHouseUsernameAndStatus(waveHouseUsername, Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền theo tài khoản kho!"));

        return CashOrdersResponse.cashOrdersResponse(cashOrders, "Lấy danh sách đơn thu tiền theo tài khoản kho thành công!", "OK");
    }


    @Override
    public CashOrdersByDatesResponse historyCashOrdersByShipperUsernameAndShipperHold(String shipperUsername, boolean shipperHold, boolean shipping) {
        deliverService.checkExistByTypeWorkShipperByUsername(shipperUsername);
        List<CashOrder> cashOrders = getCashOrdersByShipperUsername(shipperUsername, shipperHold, shipping);
        String message = greetingMessageWithResponseShipperHold(shipperHold, shipping);


        return CashOrdersByDatesResponse.cashOrdersByDatesResponse(cashOrders, message);
    }


    @Override
    public CashOrdersByDatesResponse historyCashOrdersByWaveHouseUsernameAndWaveHouseHold(String waveHouseUsername,
                                                                                          boolean waveHouseHold,
                                                                                          boolean handlePayment) {
        deliverService.checkExistByTypeWorkWarehouseByUsername(waveHouseUsername);
        List<CashOrder> cashOrders = cashOrderRepository
                .findAllByWaveHouseUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
                        waveHouseUsername, waveHouseHold, false, handlePayment, Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền theo tài khoản kho!"));
        String message = greetingMessageWithResponseWaveHouseHold(waveHouseHold, handlePayment);
        return CashOrdersByDatesResponse.cashOrdersByDatesResponse(cashOrders, message);
    }

    private String greetingMessageWithResponseWaveHouseHold(boolean waveHouseHold, boolean handlePayment) {
        if (!waveHouseHold && !handlePayment) {
            return "Lấy danh sách đơn thu tiền theo tài khoản kho chờ xác nhận đã nhận tiền thành công!";
        } else if (!waveHouseHold && handlePayment) {
            return "Lấy danh sách đơn thu tiền theo tài khoản kho đã xác nhận đã nhận tiền thành công!";
        }
        return "Lấy danh sách đơn thu tiền theo tài khoản kho đang giữ tiền thành công!";
    }


    @Override
    public boolean checkCashOrderByOrderIdWithHandlePaymentAndStatus(UUID orderId, boolean shipperHold, boolean waveHouseHold, boolean handlePayment, Status status) {
        return cashOrderRepository
                .existsByOrderIdAndShipperHoldAndWaveHouseHoldAndHandlePaymentAndStatus(orderId, shipperHold, waveHouseHold, handlePayment, status);
    }


    @Override
    public boolean checkCashOrderByOrderIdWithHandlePayment(UUID orderId, boolean shipperHold, boolean waveHouseHold, boolean handlePayment) {
        return cashOrderRepository
                .existsByTransportIdAndShipperHoldAndWaveHouseHoldAndHandlePayment(orderId, shipperHold, waveHouseHold, handlePayment);
    }


    private String generateMessage(boolean shipperHold, boolean waveHouseHold) {
        if (!shipperHold && !waveHouseHold) {
            return "Đã gửi tiền cho kho thành công!";
        } else if (!shipperHold && waveHouseHold) {
            return "Kho đã nhận tiền thành công!";
        }
        return "Cập nhật danh sách đơn thu tiền thành công!";
    }


    private CashOrder createCashOrderByTransportId(UUID transportId, String shipperUsername, boolean shipperHold, Status status) {
        CashOrder cashOrder = new CashOrder();
        cashOrder.setTransportId(transportId);
        cashOrder.setOrderId(getOrderIdByTransportId(transportId));
        cashOrder.setShipperUsername(shipperUsername);
        cashOrder.setMoney(getMoneyByOrderId(cashOrder.getOrderId()));
        cashOrder.setShipperHold(shipperHold);
        cashOrder.setWaveHouseUsername(null);
        cashOrder.setWaveHouseHold(false);
        cashOrder.setHandlePayment(false);
        cashOrder.setStatus(status);
        cashOrder.setCreateAt(LocalDateTime.now());
        cashOrder.setUpdateAt(LocalDateTime.now());

        return cashOrder;
    }


    private List<CashOrder> getCashOrdersByCashOrderIds(List<UUID> cashOrderIds) {
        return cashOrderRepository.findAllByCashOrderIdInAndStatus(cashOrderIds, Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền danh sách mã đơn thu tiền!"));
    }


    private CashOrder getCashOrderByTransportId(UUID transportId) {
        checkExistCashOrderByTransportId(transportId);

        return cashOrderRepository.findByTransportId(transportId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn thu tiền theo mã vận chuyển!"));
    }


    private UUID getOrderIdByTransportId(UUID transportId) {
        return transportRepository.findByTransportId(transportId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng để tạo đơn thu tiền!"))
                .getOrderId();
    }


    private Long getMoneyByOrderId(UUID orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng để lấy tổng tiền thanh toán!"));

        return order.getPaymentMethod().equals("COD") ? order.getTotalPrice() : 0L;
    }


    private String greetingMessageWithResponseShipperHold(boolean shipperHold, boolean shipping) {
        if (!shipperHold && !shipping) {
            return "Lấy danh sách đơn thu tiền theo tài khoản shipper đã nộp tiền về kho thành công!";
        } else if (!shipperHold && shipping) {
            return "Lấy danh sách đơn thu tiền theo tài khoản shipper đang giao thành công!";
        }
        return "Lấy danh sách đơn thu tiền theo tài khoản shipper đang giữ tiền thành công!";
    }


    private List<CashOrder> getCashOrdersByShipperUsername(String shipperUsername, boolean shipperHold, boolean shipping) {
        if (!shipperHold && !shipping) {
            return cashOrderRepository.findAllByShipperUsernameAndShipperHoldAndWaveHouseUsernameNotNull(shipperUsername, false)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền theo tài khoản shipper đã giao!"));
        } else if (!shipperHold && shipping) {
            return cashOrderRepository.findAllByShipperUsernameAndShipperHoldAndWaveHouseUsernameNull(shipperUsername, false)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền theo tài khoản shipper chưa giao!"));
        } else {
            return cashOrderRepository.findAllByShipperUsernameAndShipperHoldAndWaveHouseUsernameNotNull(shipperUsername, true)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách đơn thu tiền theo tài khoản shipper đang giữ tiền!"));
        }
    }


    private void updateCashOrderByShipperHold(CashOrder cashOrder, boolean shipperHold) {
        cashOrder.setShipperHold(shipperHold);
        cashOrder.setUpdateAt(LocalDateTime.now());
        try {
            cashOrderRepository.save(cashOrder);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi cập nhật đơn thu tiền!");
        }
    }


    private void saveCashOrder(CashOrder cashOrder) {
        try {
            cashOrderRepository.save(cashOrder);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi tạo đơn thu tiền!");
        }
    }


    private void checkExistCashOrderByTransportId(UUID transportId) {
        if (!cashOrderRepository.existsByTransportId(transportId)) {
            throw new BadRequestException("Đơn thu tiền chưa tồn tại!");
        }
    }


    //    private void checkExistCashOrdersByCashOrderIdsAndStatus(List<UUID> cashOrderIds, String username, boolean waveHouseHold) {
//        if (!waveHouseHold) {
//            if (!cashOrderRepository.existsAllByCashOrderIdInAndShipperUsernameAndStatus(cashOrderIds, username, Status.ACTIVE)) {
//                throw new BadRequestException("Danh sách đơn thu tiền chưa tồn tại theo tài khoản shipper!");
//            }
//
//            if (cashOrderRepository
//                    .existsAllByCashOrderIdInAndShipperUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
//                            cashOrderIds, username, false, false, false, Status.ACTIVE)) {
//                throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho!");
//            }
//
//            if (cashOrderRepository
//                    .existsAllByCashOrderIdInAndShipperUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
//                            cashOrderIds, username, false, false, true, Status.ACTIVE)) {
//                throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho và đã thanh toán cho các cửa hàng!");
//            }
//
//            if (cashOrderRepository
//                    .existsAllByCashOrderIdInAndShipperUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
//                            cashOrderIds, username, true, false, false, Status.ACTIVE)) {
//                throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho và đang giữ tiền!");
//            }
//
//        } else {
//            if (!cashOrderRepository.existsAllByCashOrderIdInAndWaveHouseUsernameAndStatus(cashOrderIds, username, Status.ACTIVE)) {
//                throw new BadRequestException("Danh sách đơn thu tiền chưa tồn tại theo tài khoản kho!");
//            }
//
//
//            if (cashOrderRepository
//                    .existsAllByCashOrderIdInAndWaveHouseUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
//                            cashOrderIds, username, true, false, false, Status.ACTIVE)) {
//                throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho!");
//            }
//
//            if (cashOrderRepository
//                    .existsAllByCashOrderIdInAndWaveHouseUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
//                            cashOrderIds, username, false, false, true, Status.ACTIVE)) {
//                throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho và đã thanh toán cho các cửa hàng!");
//            }
//
//        }
//
//    }


    private void checkExistCashOrdersByCashOrderIds(List<UUID> cashOrderIds, String username, boolean waveHouseHold) {
        if (waveHouseHold) {
            checkWaveHouseHoldOrders(cashOrderIds, username);
        } else {
            checkShipperOrders(cashOrderIds, username);
        }
    }

    private void checkShipperOrders(List<UUID> cashOrderIds, String username) {
        if (!cashOrderRepository.existsAllByCashOrderIdInAndShipperUsernameAndStatus(cashOrderIds, username, Status.ACTIVE)) {
            throw new BadRequestException("Danh sách đơn thu tiền chưa tồn tại theo tài khoản shipper!");
        }

        if (cashOrderRepository.existsAllByCashOrderIdInAndShipperUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
                cashOrderIds, username, false, false, false, Status.ACTIVE)) {
            throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho!");
        }

        if (cashOrderRepository.existsAllByCashOrderIdInAndShipperUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
                cashOrderIds, username, false, false, true, Status.ACTIVE)) {
            throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho và đã thanh toán cho các cửa hàng!");
        }

        if (cashOrderRepository.existsAllByCashOrderIdInAndShipperUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
                cashOrderIds, username, true, false, false, Status.ACTIVE)) {
            throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho và đang giữ tiền!");
        }
    }

    private void checkWaveHouseHoldOrders(List<UUID> cashOrderIds, String username) {
        if (!cashOrderRepository.existsAllByCashOrderIdInAndWaveHouseUsernameAndStatus(cashOrderIds, username, Status.ACTIVE)) {
            throw new BadRequestException("Danh sách đơn thu tiền chưa tồn tại theo tài khoản kho!");
        }

        if (cashOrderRepository.existsAllByCashOrderIdInAndWaveHouseUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
                cashOrderIds, username, true, false, false, Status.ACTIVE)) {
            throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho!");
        }

        if (cashOrderRepository.existsAllByCashOrderIdInAndWaveHouseUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(
                cashOrderIds, username, false, false, true, Status.ACTIVE)) {
            throw new BadRequestException("Danh sách đơn thu tiền đã được giao cho kho và đã thanh toán cho các cửa hàng!");
        }
    }


    private boolean checkStatusOrderByOrderId(UUID orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng để kiểm tra trạng thái!"))
                .getStatus().equals(OrderStatus.COMPLETED);
    }


    private void checkExistWaveHouseByUsername(String waveHouseUsername) {
        if (!deliverRepository.existsByCustomerUsername(waveHouseUsername)) {
            throw new BadRequestException("Không tồn tại nhân viên giao hàng theo tái khoản!");
        }
        Deliver deliver = deliverRepository.findByCustomerUsername(waveHouseUsername)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên giao hàng theo tài khoản!"));
        if (!deliver.getTypeWork().equals(TypeWork.PROVIDER) && !deliver.getTypeWork().equals(TypeWork.WAREHOUSE) &&
                !deliver.getTypeWork().equals(TypeWork.MANAGER)) {
            throw new BadRequestException("Nhân viên giao hàng không có quyền thực hiện thao tác với đơn thu tiền!");
        }
    }
}

