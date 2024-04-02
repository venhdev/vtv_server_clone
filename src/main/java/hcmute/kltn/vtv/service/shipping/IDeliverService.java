package hcmute.kltn.vtv.service.shipping;

import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.extra.TransportStatus;

public interface IDeliverService {
    Deliver checkTypeWorkDeliverWithTransportStatus(String username, TransportStatus transportStatus);
}
