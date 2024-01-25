package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.authentication.service.IAuthenticationService;
import hcmute.kltn.vtv.model.data.manager.request.TransportProviderRegisterRequest;
import hcmute.kltn.vtv.model.data.shipping.response.ListTransportProviderResponse;
import hcmute.kltn.vtv.model.data.shipping.response.TransportProviderResponse;
import hcmute.kltn.vtv.model.entity.location.Province;
import hcmute.kltn.vtv.model.entity.shipping.TransportProvider;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.extra.Role;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.shipping.TransportProviderRepository;
import hcmute.kltn.vtv.service.location.IProvinceService;
import hcmute.kltn.vtv.service.manager.IManagerCustomerService;
import hcmute.kltn.vtv.service.manager.IManagerTransportProvider;
import hcmute.kltn.vtv.service.shipping.ITransportProvider;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerTransportProviderImpl implements IManagerTransportProvider {

    private final TransportProviderRepository transportProviderRepository;
    private final ICustomerService customerService;
    private final IAuthenticationService authenticationService;
    private final IManagerCustomerService managerCustomerService;
    private final IProvinceService provinceService;
    private final ITransportProvider transportProviderService;

    @Override
    @Transactional
    public TransportProviderResponse addNewTransportProvider(TransportProviderRegisterRequest request) {

        if (customerService.checkUsernameExist(request.getRegisterRequest().getUsername())) {
            throw new BadRequestException("Tài khoản đã tồn tại.");
        }

        List<Province> provinces = provinceService.getPronvincesByProvinceCode(request.getProvincesCode());
        Customer customer = authenticationService.addNewCustomer(request.getRegisterRequest());
        managerCustomerService.updateRoleWithCustomer(customer.getCustomerId(), Role.PROVIDER);

        TransportProvider transportProvider = createTransportProvider(request);
        transportProvider.setCustomer(customer);
        transportProvider.setProvinces(provinces);

        try {
            transportProviderRepository.save(transportProvider);

            return transportProviderService.transportProviderResponse(transportProvider,
                    "Đăng ký nhà vận chuyển thành công.", "Success");
        } catch (Exception e) {
            throw new BadRequestException("Đăng ký nhà vận chuyển thất bại.");
        }
    }


    @Override
    public ListTransportProviderResponse getAllTransportProviders() {
        List<TransportProvider> transportProviders = transportProviderRepository.findAllByStatus(Status.ACTIVE)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy nhà vận chuyển nào."));

        return transportProviderService.listTransportProvidersResponse(transportProviders);
    }

    private TransportProvider createTransportProvider(TransportProviderRegisterRequest request) {
        TransportProvider transportProvider = new TransportProvider();
        transportProvider.setFullName(request.getFullName());
        transportProvider.setShortName(request.getShortName());
        transportProvider.setUsernameAdded(request.getUsernameAdded());
        transportProvider.setStatus(Status.ACTIVE);
        transportProvider.setCreateAt(LocalDateTime.now());
        transportProvider.setUpdateAt(LocalDateTime.now());

        return transportProvider;
    }





}
