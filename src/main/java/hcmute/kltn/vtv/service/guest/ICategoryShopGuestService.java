package hcmute.kltn.vtv.service.guest;

import hcmute.kltn.vtv.model.data.vendor.response.CategoryShopResponse;
import hcmute.kltn.vtv.model.data.vendor.response.ListCategoryShopResponse;

public interface ICategoryShopGuestService {
    CategoryShopResponse getCategoryShopById(Long categoryShopId);

    ListCategoryShopResponse getCategoryShopsByShopId(Long shopId);
}
