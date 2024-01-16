package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.manager.request.DeliverRequest;
import hcmute.kltn.vtv.model.data.manager.response.DeliverResponse;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerDeliverService {
    @Transactional
    DeliverResponse addNewDeliver(DeliverRequest request);
}
