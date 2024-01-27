package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.manager.request.TransportProviderRegisterRequest;
import hcmute.kltn.vtv.model.data.manager.request.UpdateTransportProviderWithProvincesRequest;
import hcmute.kltn.vtv.model.data.shipping.response.ListTransportProviderResponse;
import hcmute.kltn.vtv.model.data.shipping.response.TransportProviderResponse;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerTransportProvider {
    @Transactional
    TransportProviderResponse addNewTransportProvider(TransportProviderRegisterRequest request);

    @Transactional
    TransportProviderResponse updateTransportProviderWithProvinces(UpdateTransportProviderWithProvincesRequest request);

    ListTransportProviderResponse getAllTransportProviders();
}
