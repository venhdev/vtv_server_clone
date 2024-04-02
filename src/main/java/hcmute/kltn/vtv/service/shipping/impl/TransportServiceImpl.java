package hcmute.kltn.vtv.service.shipping.impl;

import hcmute.kltn.vtv.model.data.shipping.request.TransportHandleRequest;
import hcmute.kltn.vtv.model.entity.shipping.Transport;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import hcmute.kltn.vtv.repository.shipping.TransportRepository;
import hcmute.kltn.vtv.service.shipping.ITransportHandleService;
import hcmute.kltn.vtv.service.shipping.ITransportService;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements ITransportService {

    private final TransportRepository transportRepository;
    private final ITransportHandleService transportHandleService;


    @Transactional
    @Override
    public Transport addNewTransport(UUID orderId, String wardCodeShop, String wardCodeCustomer, String username) {
        Transport transport = createTransport(orderId, wardCodeShop, wardCodeCustomer);
        try {
            return transportRepository.save(transport);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi tạo mới một đơn vận chuyển mới!");
        }
    }

    @Override
    @Transactional
    public Transport updateStatusTransport(UUID orderId, String wardCode, String username,
                                           boolean handled, TransportStatus transportStatus) {
        Transport transport = getTransportByOrderId(orderId);
        transport.setTransportStatus(transportStatus);
        transport.setUpdateAt(LocalDateTime.now());
        try {

            return transportRepository.save(transport);
        } catch (Exception e) {
            throw new InternalServerErrorException("Lỗi khi cập nhật trạng thái đơn vận chuyển!");
        }
    }


    @Override
    public Transport getTransportById(UUID transportId) {
        return transportRepository.findByTransportId(transportId)
                .orElseThrow(() -> new InternalServerErrorException("Không tìm thấy đơn vận chuyển với id: " + transportId));
    }


    @Override
    public Transport getTransportByOrderId(UUID orderId) {
        Transport transport = transportRepository.findByOrderId(orderId)
                .orElseThrow(() -> new InternalServerErrorException("Không tìm thấy đơn vận chuyển theo mã đơn hàng: " + orderId));

        transport.setTransportHandles(transportHandleService.getAllTransportHandleByTransportId(transport.getTransportId()));
        return transport;
    }


    public Transport createTransport(UUID orderId, String wardCodeShop, String wardCodeCustomer) {
        Transport transport = new Transport();
        transport.setOrderId(orderId);
        transport.setWardCodeShop(wardCodeShop);
        transport.setWardCodeCustomer(wardCodeCustomer);
        transport.setTransportStatus(TransportStatus.WAITING);
        transport.setCreateAt(LocalDateTime.now());
        transport.setUpdateAt(LocalDateTime.now());

        return transport;

    }


}
