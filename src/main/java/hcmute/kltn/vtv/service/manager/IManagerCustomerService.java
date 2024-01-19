package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.manager.response.ListCustomerManagerResponse;
import hcmute.kltn.vtv.model.data.user.response.ProfileCustomerResponse;
import hcmute.kltn.vtv.model.extra.Role;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerCustomerService {
    ListCustomerManagerResponse getListCustomerByStatus(int size, int page, Status status);

    ListCustomerManagerResponse getListCustomerByStatusSort(int size, int page, Status status, String sort);

    ListCustomerManagerResponse searchCustomerByStatus(int size, int page, Status status, String search);

    ProfileCustomerResponse getCustomerDetailByCustomerId(Long customerId);

    void checkRequestPageParams(int page, int size);

    void checkRequestSortParams(String sort);

    @Transactional
    void updateRoleWithCustomer(Long customerId, Role role);
}
