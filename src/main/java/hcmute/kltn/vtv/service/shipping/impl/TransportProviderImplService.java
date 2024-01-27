package hcmute.kltn.vtv.service.shipping.impl;

import hcmute.kltn.vtv.model.data.shipping.request.UpdateTransportProviderRequest;
import hcmute.kltn.vtv.model.data.shipping.response.ListTransportProviderResponse;
import hcmute.kltn.vtv.model.data.shipping.response.TransportProviderResponse;
import hcmute.kltn.vtv.model.dto.shipping.TransportProviderDTO;
import hcmute.kltn.vtv.model.entity.shipping.TransportProvider;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.shipping.TransportProviderRepository;
import hcmute.kltn.vtv.service.shipping.ITransportProviderService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportProviderImplService implements ITransportProviderService {
    private final TransportProviderRepository transportProviderRepository;


    @Transactional
    @Override
    public TransportProviderResponse updateTransportProviderResponse(UpdateTransportProviderRequest request) {

        TransportProvider transportProvider = updateTransportProvider(request);

        
        checkOwnerUpdateExist(request.getUsername(), request.getTransportProviderId());
        checkEmailUpdateExist(request.getEmail(), request.getTransportProviderId());
        checkPhoneUpdateExist(request.getPhone(), request.getTransportProviderId());

        try {
            transportProviderRepository.save(transportProvider);
            return transportProviderResponse(transportProvider, "Cập nhật nhà vận chuyển thành công.", "Success");
        } catch (Exception e) {
            throw new NotFoundException("Cập nhật nhà vận chuyển thất bại.");
        }
    }


    private void checkOwnerUpdateExist(String username, Long transportProviderId) {
        TransportProvider transportProvider = transportProviderRepository.findByCustomerUsername(username)
                .orElse(null);

        if (transportProvider == null || !transportProvider.getTransportProviderId().equals(transportProviderId)) {
            throw new BadRequestException("Tài khoản không có quyền cập nhật nhà vận chuyển này.");
        }
    }

    private void checkEmailUpdateExist(String email, Long transportProviderId) {
        TransportProvider transportProvider = transportProviderRepository.findByEmail(email)
                .orElse(null);
        if (transportProvider != null && !transportProvider.getTransportProviderId().equals(transportProviderId)) {
            throw new BadRequestException("Email nhà vận chuyển đã tồn tại.");
        }
    }


    private void checkPhoneUpdateExist(String phone, Long transportProviderId) {
        TransportProvider transportProvider = transportProviderRepository.findByPhone(phone)
                .orElse(null);
        if (transportProvider != null && !transportProvider.getTransportProviderId().equals(transportProviderId)) {
            throw new BadRequestException("Số điện thoại nhà vận chuyển đã tồn tại.");
        }
    }

    private TransportProvider updateTransportProvider(UpdateTransportProviderRequest request) {
        TransportProvider transportProvider = getTransportProviderByTransportProviderId(request.getTransportProviderId());

        transportProvider.setFullName(request.getFullName());
        transportProvider.setShortName(request.getShortName());
        transportProvider.setEmail(request.getEmail());
        transportProvider.setPhone(request.getPhone());
        transportProvider.setUpdateAt(LocalDateTime.now());

        return transportProvider;
    }


    @Override
    public TransportProviderResponse getTransportProviderById(Long id) {
        TransportProvider transportProvider = getTransportProviderByTransportProviderId(id);

        return transportProviderResponse(transportProvider, "Lấy thông tin nhà vận chuyển thành công.", "OK");
    }

    public TransportProvider getTransportProviderByTransportProviderId(Long id) {
        TransportProvider transportProvider = transportProviderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhà vận chuyển."));

        return transportProvider;
    }


    @Override
    public ListTransportProviderResponse getAllTransportProvidersNotProvince() {

        List<TransportProvider> transportProviders = transportProviderRepository.findAllByStatus(Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhà vận chuyển nào."));


        return listTransportProvidersNotProvinceResponse(transportProviders);
    }

    @Override
    public ListTransportProviderResponse getAllTransportProviders() {

        List<TransportProvider> transportProviders = transportProviderRepository.findAllByStatus(Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhà vận chuyển nào."));


        return listTransportProvidersResponse(transportProviders);
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