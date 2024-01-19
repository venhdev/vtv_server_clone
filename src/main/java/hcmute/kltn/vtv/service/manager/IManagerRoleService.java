package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.manager.response.ListManagerPageResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.extra.Role;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerRoleService {
    @Transactional
    ManagerResponse managerAddRole(String usernameAdded, String usernameCustomer, Role role);

    @Transactional
    ManagerResponse managerUpdateRole(String usernameAdded, String usernameCustomer, Role role);

    @Transactional
    ManagerResponse managerDeleteRole(String usernameAdded, String usernameCustomer, Role role);

    ListManagerPageResponse getListManagerPageByRole(Role role, int page, int size);

    void checkRequestPageParams(int page, int size);
}
