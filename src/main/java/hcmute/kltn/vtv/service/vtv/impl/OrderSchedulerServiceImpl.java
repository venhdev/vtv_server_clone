package hcmute.kltn.vtv.service.vtv.impl;

import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.repository.user.OrderRepository;
import hcmute.kltn.vtv.service.vtv.IOrderSchedulerService;
import hcmute.kltn.vtv.service.wallet.IWalletService;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderSchedulerServiceImpl implements IOrderSchedulerService {

    private final OrderRepository orderRepository;
    private final IWalletService walletService;

    @Override
    @Transactional
    public void autoCompleteOrderAfterFiveDayDelivered() {
        List<Order> orders = orderRepository.findAllByStatus(OrderStatus.DELIVERED)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng!"));

        checkAndUpdateOrderStatus(orders);
    }

    private void checkAndUpdateOrderStatus(List<Order> orders) {
        for (Order order : orders) {
            LocalDateTime deliveredDate = order.getUpdateAt();
            LocalDateTime fiveDaysAfterDelivery = deliveredDate.plusDays(5);
            if (LocalDateTime.now().isAfter(fiveDaysAfterDelivery)) {
                // Nếu đã qua 5 ngày kể từ ngày giao hàng, cập nhật trạng thái đơn hàng thành đã nhận hàng
                order.setStatus(OrderStatus.COMPLETED);
                try {
                    walletService.updateWalletByUsername(
                            order.getShop().getCustomer().getUsername(),
                            order.getOrderId(),
                            order.getTotalPrice() * 96 / 100 - order.getDiscountShop(),
                            "COMPLETED_ORDER");
                    orderRepository.save(order);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
