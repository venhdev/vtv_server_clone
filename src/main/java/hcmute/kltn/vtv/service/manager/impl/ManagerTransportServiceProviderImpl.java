package hcmute.kltn.vtv.service.manager.impl;


import hcmute.kltn.vtv.authentication.service.IAuthenticationService;
import hcmute.kltn.vtv.model.data.admin.request.TransportServiceProviderRegisterRequest;
import hcmute.kltn.vtv.model.data.shipping.response.ListTransportServiceProvidersResponse;
import hcmute.kltn.vtv.model.data.shipping.response.TransportServiceProviderResponse;
import hcmute.kltn.vtv.model.dto.shipping.TransportServiceProviderDTO;
import hcmute.kltn.vtv.model.entity.location.Province;
import hcmute.kltn.vtv.model.entity.shipping.TransportServiceProvider;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.extra.Role;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.shipping.TransportServiceProviderRepository;
import hcmute.kltn.vtv.service.location.IProvinceService;
import hcmute.kltn.vtv.service.manager.IManagerCustomerService;
import hcmute.kltn.vtv.service.manager.IManagerTransportServiceProvider;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerTransportServiceProviderImpl implements IManagerTransportServiceProvider {

    private final TransportServiceProviderRepository transportServiceProviderRepository;
    private final ICustomerService customerService;
    private final IAuthenticationService authenticationService;
    private final IManagerCustomerService managerCustomerService;
    private final IProvinceService provinceService;



    @Override
    @Transactional
    public TransportServiceProviderResponse addNewTransportServiceProvider(TransportServiceProviderRegisterRequest request) {

        if (customerService.checkUsernameExist(request.getRegisterRequest().getUsername())) {
            throw new BadRequestException("Tài khoản đã tồn tại.");
        }

        List<Province> provinces = provinceService.getPronvincesByProvinceCode(request.getProvincesCode());
        Customer customer = authenticationService.addNewCustomer(request.getRegisterRequest());
        managerCustomerService.updateRoleWithCustomer(customer.getCustomerId(), Role.PROVIDER);

        TransportServiceProvider transportServiceProvider = createTransportServiceProvider(request);
        transportServiceProvider.setCustomer(customer);
        transportServiceProvider.setProvinces(provinces);

        try {
            transportServiceProviderRepository.save(transportServiceProvider);

           return transportServiceProviderResponse(transportServiceProvider);
        } catch (Exception e) {
            throw new BadRequestException("Đăng ký nhà vận chuyển thất bại.");
        }
    }

    public ListTransportServiceProvidersResponse getAllTransportServiceProviders(){
        List<TransportServiceProvider> transportServiceProviders = transportServiceProviderRepository.findAllByStatus(Status.ACTIVE)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy nhà vận chuyển nào."));

        return listTransportServiceProvidersResponse(transportServiceProviders);
    }


    private TransportServiceProvider createTransportServiceProvider(TransportServiceProviderRegisterRequest request) {
        TransportServiceProvider transportServiceProvider = new TransportServiceProvider();
        transportServiceProvider.setFullName(request.getFullName());
        transportServiceProvider.setShortName(request.getShortName());
        transportServiceProvider.setUsernameAdded(request.getUsernameAdded());
        transportServiceProvider.setStatus(Status.ACTIVE);
        transportServiceProvider.setCreateAt(LocalDateTime.now());
        transportServiceProvider.setUpdateAt(LocalDateTime.now());

        return transportServiceProvider;
    }

    private TransportServiceProviderResponse transportServiceProviderResponse(TransportServiceProvider transportServiceProvider){
        TransportServiceProviderResponse response = new TransportServiceProviderResponse();
        response.setTransportServiceProviderDTO(TransportServiceProviderDTO.convertEntityToDTO(transportServiceProvider));
        response.setMessage("Đăng ký nhà vận chuyển thành công.");
        response.setStatus("Success");
        response.setCode(200);
        return response;
    }

    private ListTransportServiceProvidersResponse listTransportServiceProvidersResponse(List<TransportServiceProvider> transportServiceProviders){
        ListTransportServiceProvidersResponse response = new ListTransportServiceProvidersResponse();
        response.setTransportServiceProviderDTOs(TransportServiceProviderDTO.convertEntitiesToDTOs(transportServiceProviders));
        response.setCount(response.getTransportServiceProviderDTOs().size());
        response.setMessage("Lấy danh sách nhà vận chuyển thành công.");
        response.setStatus("OK");
        response.setCode(200);
        return response;
    }






}
