package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.request.CategoryShopRequest;
import hcmute.kltn.vtv.model.data.vendor.response.CategoryShopResponse;
import org.springframework.transaction.annotation.Transactional;

public interface ICategoryShopService {
    @Transactional
    CategoryShopResponse addNewCategoryShop(CategoryShopRequest request, String username);
}
