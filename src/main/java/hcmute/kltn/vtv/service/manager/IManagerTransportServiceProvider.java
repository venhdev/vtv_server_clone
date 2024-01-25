package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.admin.request.TransportServiceProviderRegisterRequest;
import hcmute.kltn.vtv.model.data.shipping.response.TransportServiceProviderResponse;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerTransportServiceProvider {
    @Transactional
    TransportServiceProviderResponse addNewTransportServiceProvider(TransportServiceProviderRegisterRequest request);
}
