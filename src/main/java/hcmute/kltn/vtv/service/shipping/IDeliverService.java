package hcmute.kltn.vtv.service.shipping;

import hcmute.kltn.vtv.model.data.shipping.response.DeliverResponse;
import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.extra.TransportStatus;

public interface IDeliverService {
    DeliverResponse getDeliverResponseByUsername(String username);

    Deliver checkTypeWorkDeliverWithTransportStatus(String username, TransportStatus transportStatus);

    Deliver getDeliverByUsername(String username);

    void checkExistByTypeWorkShipperByUsername(String username);

    void checkExistByTypeWorkWarehouseByUsername(String username);
}
