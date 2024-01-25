package hcmute.kltn.vtv.service.shipping.impl;

import hcmute.kltn.vtv.model.data.shipping.response.ListTransportProviderResponse;
import hcmute.kltn.vtv.model.data.shipping.response.TransportProviderResponse;
import hcmute.kltn.vtv.model.dto.shipping.TransportProviderDTO;
import hcmute.kltn.vtv.model.entity.shipping.TransportProvider;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.shipping.TransportProviderRepository;
import hcmute.kltn.vtv.service.shipping.ITransportProvider;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportProviderImpl implements ITransportProvider {
    private final TransportProviderRepository transportProviderRepository;


    @Override
    public TransportProviderResponse getTransportProviderById(Long id) {
        TransportProvider transportProvider = transportProviderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhà vận chuyển."));

        return transportProviderResponse(transportProvider, "Lấy thông tin nhà vận chuyển thành công.", "OK");
    }


    @Override
    public ListTransportProviderResponse getAllTransportProvidersNotProvince() {

        List<TransportProvider> transportProviders = transportProviderRepository.findAllByStatus(Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhà vận chuyển nào."));


        return  listTransportProvidersNotProvinceResponse(transportProviders);
    }

    @Override
    public ListTransportProviderResponse getAllTransportProviders() {

        List<TransportProvider> transportProviders = transportProviderRepository.findAllByStatus(Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhà vận chuyển nào."));


        return  listTransportProvidersResponse(transportProviders);
    }

    @Override
    public ListTransportProviderResponse listTransportProvidersNotProvinceResponse(List<TransportProvider> transportProviders) {
        ListTransportProviderResponse response = new ListTransportProviderResponse();
        response.setTransportProviderDTOs(TransportProviderDTO.convertNotProvinceEntitiesToDTOs(transportProviders));
        response.setCount(response.getTransportProviderDTOs().size());
        response.setMessage("Lấy danh sách nhà vận chuyển thành công.");
        response.setStatus("OK");
        response.setCode(200);
        return response;
    }


    @Override
    public TransportProviderResponse transportProviderResponse(TransportProvider transportProvider, String meesage, String status) {
        TransportProviderResponse response = new TransportProviderResponse();
        response.setTransportProviderDTO(TransportProviderDTO.convertEntityToDTO(transportProvider));
        response.setMessage(meesage);
        response.setStatus(status);
        response.setCode(200);
        return response;
    }


    @Override
    public ListTransportProviderResponse listTransportProvidersResponse(List<TransportProvider> transportProviders) {
        ListTransportProviderResponse response = new ListTransportProviderResponse();
        response.setTransportProviderDTOs(TransportProviderDTO.convertEntitiesToDTOs(transportProviders));
        response.setCount(response.getTransportProviderDTOs().size());
        response.setMessage("Lấy danh sách nhà vận chuyển thành công.");
        response.setStatus("OK");
        response.setCode(200);
        return response;
    }

}