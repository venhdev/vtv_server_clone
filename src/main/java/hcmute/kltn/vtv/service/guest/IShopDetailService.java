package hcmute.kltn.vtv.service.guest;

import hcmute.kltn.vtv.model.data.guest.ShopDetailResponse;

public interface IShopDetailService {

    ShopDetailResponse getShopDetailByShopId(Long shopId, String username);
}
