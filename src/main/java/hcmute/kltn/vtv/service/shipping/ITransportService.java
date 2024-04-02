package hcmute.kltn.vtv.service.shipping;

import hcmute.kltn.vtv.model.data.shipping.response.TransportResponse;
import hcmute.kltn.vtv.model.entity.shipping.Transport;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ITransportService {


    @Transactional
    Transport addNewTransport(UUID orderId);

    @Transactional
    Transport updateStatusTransport(Transport transport, String wardCode, String username,
                                    boolean handled, TransportStatus transportStatus);

    Transport updateStatusTransportByOrderId(UUID orderId, String wardCode, String username,
                                             boolean handled, TransportStatus transportStatus);

    Transport updateStatusTransportByTransportId(UUID transportId, String wardCode, String username,
                                                 boolean handled, TransportStatus transportStatus);

    Transport getTransportById(UUID transportId);

    Transport getTransportByOrderId(UUID orderId);

    @Transactional
    TransportResponse updateStatusByDeliver(UUID transportId, String username, boolean handled, TransportStatus transportStatus, String wardCode);
}
