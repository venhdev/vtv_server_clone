package hcmute.kltn.vtv.service.admin;

import hcmute.kltn.vtv.model.data.manager.response.ListManagerPageResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerAdminService {
    @Transactional
    ManagerResponse addRoleManager(String username, String usernameCustomer);

    @Transactional
    ManagerResponse removeRoleManager(String username, String usernameCustomer);

    ListManagerPageResponse getManagerPage(int page, int size);

    ListManagerPageResponse getManagerPageByUsername(int page, int size, String username);

    ListManagerPageResponse getManagerPageByStatus(int page, int size, Status status);

    void checkRequestPageParams(int page, int size);
}
