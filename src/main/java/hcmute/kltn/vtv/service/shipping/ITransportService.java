package hcmute.kltn.vtv.service.shipping;

import hcmute.kltn.vtv.model.data.shipping.response.TransportResponse;
import hcmute.kltn.vtv.model.entity.shipping.Transport;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ITransportService {
    @Transactional
    Transport addNewTransport(UUID orderId, String wardCodeShop, String wardCodeCustomer, String username);

    @Transactional
    Transport updateStatusTransport(UUID orderId, String wardCode, String username,
                                    boolean handled, TransportStatus transportStatus);

    Transport getTransportById(UUID transportId);

    Transport getTransportByOrderId(UUID orderId);
}
