package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.extra.Role;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerRoleService {
    @Transactional
    ManagerResponse managerAddRole(String usernameAdded, String usernameCustomer, Role role);
}
