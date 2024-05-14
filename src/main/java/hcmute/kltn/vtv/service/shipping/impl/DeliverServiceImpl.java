package hcmute.kltn.vtv.service.shipping.impl;

import hcmute.kltn.vtv.model.data.shipping.response.DeliverResponse;
import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import hcmute.kltn.vtv.model.extra.TypeWork;
import hcmute.kltn.vtv.repository.shipping.DeliverRepository;
import hcmute.kltn.vtv.service.shipping.IDeliverService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliverServiceImpl implements IDeliverService {

    private final DeliverRepository deliverRepository;



    @Override
    public DeliverResponse getDeliverResponseByUsername(String username) {
        Deliver deliver = getDeliverByUsername(username);
        return DeliverResponse.deliverResponse(deliver, "Lấy thông tin nhân viên giao hàng thành công!", "OK");
    }


    @Override
    public Deliver checkTypeWorkDeliverWithTransportStatus(String username, TransportStatus transportStatus) {
        Deliver deliver = getDeliverByUsername(username);
        if (!checkTypeWorkDeliverWithTransportStatus(deliver.getTypeWork(), transportStatus)) {
            throw new BadRequestException("Người giao hàng không thể thực hiện hành động này!");
        }
        return deliver;
    }



    @Override
    public Deliver getDeliverByUsername(String username) {
        checkExistByUsername(username);
        return deliverRepository.findByCustomerUsername(username)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người giao hàng có tên đăng nhập: " + username));
    }


    @Override
    public void checkExistByTypeWorkShipperByUsername(String username) {

        Deliver deliver = getDeliverByUsername(username);
        if (!deliver.getTypeWork().equals(TypeWork.SHIPPER) && !deliver.getTypeWork().equals(TypeWork.MANAGER) &&
                !deliver.getTypeWork().equals(TypeWork.WAREHOUSE) && !deliver.getTypeWork().equals(TypeWork.PROVIDER)) {
            throw new BadRequestException("Bạn không phải có quyền thực hiện hành động của nhân viên giao hàng!");
        }

    }

    @Override
    public void checkExistByTypeWorkWarehouseByUsername(String username) {

        Deliver deliver = getDeliverByUsername(username);
        if (!deliver.getTypeWork().equals(TypeWork.MANAGER) &&
                !deliver.getTypeWork().equals(TypeWork.WAREHOUSE) && !deliver.getTypeWork().equals(TypeWork.PROVIDER)) {
            throw new BadRequestException("Bạn không phải có quyền thực hiện hành động của nhân kho!");
        }

    }


    private void checkExistByUsername(String username) {
        if (!deliverRepository.existsByCustomerUsername(username)) {
            throw new NotFoundException("Không tồn tại người giao hàng có tên đăng nhập: " + username);
        }
    }

    private boolean checkTypeWorkDeliverWithTransportStatus(TypeWork typeWork, TransportStatus transportStatus) {
        return switch (typeWork) {
            case SHIPPER -> transportStatus == TransportStatus.PICKED_UP ||
                    transportStatus == TransportStatus.SHIPPING || transportStatus == TransportStatus.IN_TRANSIT ||
                    transportStatus == TransportStatus.DELIVERED || transportStatus == TransportStatus.RETURNED;
            case MANAGER -> transportStatus == TransportStatus.PICKED_UP ||
                    transportStatus == TransportStatus.SHIPPING || transportStatus == TransportStatus.IN_TRANSIT ||
                            transportStatus == TransportStatus.WAREHOUSE || transportStatus == TransportStatus.DELIVERED ||
                            transportStatus == TransportStatus.COMPLETED || transportStatus == TransportStatus.RETURNED ||
                            transportStatus == TransportStatus.CANCEL;
            case PROVIDER -> transportStatus == TransportStatus.PICKED_UP ||
                    transportStatus == TransportStatus.PROCESSING || transportStatus == TransportStatus.WAITING ||
                            transportStatus == TransportStatus.WAREHOUSE || transportStatus == TransportStatus.SHIPPING ||
                            transportStatus == TransportStatus.DELIVERED || transportStatus == TransportStatus.RETURNED ||
                            transportStatus == TransportStatus.CANCEL;
            case WAREHOUSE -> transportStatus == TransportStatus.PICKED_UP ||
                    transportStatus == TransportStatus.WAREHOUSE || transportStatus == TransportStatus.DELIVERED ||
                            transportStatus == TransportStatus.RETURNED || transportStatus == TransportStatus.CANCEL;
            case TRANSIT ->
                    transportStatus == TransportStatus.IN_TRANSIT || transportStatus == TransportStatus.RETURNED ||
                            transportStatus == TransportStatus.CANCEL;
            case PICKUP ->
                    transportStatus == TransportStatus.PICKED_UP || transportStatus == TransportStatus.IN_TRANSIT ||
                            transportStatus == TransportStatus.SHIPPING || transportStatus == TransportStatus.RETURNED;
        };
    }

}
