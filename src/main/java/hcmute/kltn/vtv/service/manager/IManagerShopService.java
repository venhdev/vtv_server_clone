package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.manager.response.ListShopManagerResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerShopResponse;
import hcmute.kltn.vtv.model.data.manager.response.ShopsResponse;
import hcmute.kltn.vtv.model.extra.Status;

public interface IManagerShopService {
    ManagerShopResponse getShopById(Long id);

    ShopsResponse getShopsByStatus(int page, int size, Status status);

    ShopsResponse getShopsByNameAndStatus(int page, int size, String name, Status status);

    ListShopManagerResponse getListShop(int size, int page, Status status);

    ListShopManagerResponse getListShopByName(int size, int page, String name, Status status);

}
