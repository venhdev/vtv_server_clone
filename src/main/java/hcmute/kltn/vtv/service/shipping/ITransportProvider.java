package hcmute.kltn.vtv.service.shipping;

import hcmute.kltn.vtv.model.data.shipping.response.ListTransportProviderResponse;
import hcmute.kltn.vtv.model.data.shipping.response.TransportProviderResponse;
import hcmute.kltn.vtv.model.entity.shipping.TransportProvider;

import java.util.List;

public interface ITransportProvider {


    TransportProviderResponse getTransportProviderById(Long id);

    ListTransportProviderResponse getAllTransportProvidersNotProvince();

    ListTransportProviderResponse getAllTransportProviders();

    ListTransportProviderResponse listTransportProvidersNotProvinceResponse(List<TransportProvider> transportProviders);


    TransportProviderResponse transportProviderResponse(TransportProvider transportProvider, String meesage, String status);

    ListTransportProviderResponse listTransportProvidersResponse(List<TransportProvider> transportProviders);
}
