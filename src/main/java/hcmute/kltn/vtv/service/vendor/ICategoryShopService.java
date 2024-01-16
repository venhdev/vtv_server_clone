package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.request.CategoryShopRequest;
import hcmute.kltn.vtv.model.data.vendor.response.CategoryShopResponse;
import hcmute.kltn.vtv.model.data.vendor.response.ListCategoryShopResponse;
import hcmute.kltn.vtv.model.entity.vtv.Category;
import hcmute.kltn.vtv.model.extra.Status;

public interface ICategoryShopService {
    CategoryShopResponse addNewCategoryShop(CategoryShopRequest request);

    ListCategoryShopResponse getListCategoryShop(String username);

    CategoryShopResponse getCategoryById(Long categoryId, String username);

    CategoryShopResponse updateCategoryShop(CategoryShopRequest request);

    CategoryShopResponse updateStatusCategoryShop(Long categoryId, String username, Status status);

    Category getCategoryShopById(Long categoryId, String username);
}
