package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;

public interface IManagerService {
    ManagerResponse getManagerByUserName(String username);
}
