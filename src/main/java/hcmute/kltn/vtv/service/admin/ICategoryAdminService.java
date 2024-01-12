package hcmute.kltn.vtv.service.admin;

import hcmute.kltn.vtv.model.data.admin.request.CategoryAdminRequest;
import hcmute.kltn.vtv.model.data.admin.response.AllCategoryAdminResponse;
import hcmute.kltn.vtv.model.data.admin.response.CategoryAdminResponse;
import hcmute.kltn.vtv.model.extra.Status;

public interface ICategoryAdminService {
    CategoryAdminResponse addNewCategory(CategoryAdminRequest request);

    CategoryAdminResponse getCategoryParent(Long categoryId);

    AllCategoryAdminResponse getAllCategoryParent();

    CategoryAdminResponse updateCategoryParent(CategoryAdminRequest request);

    CategoryAdminResponse updateStatusCategoryParent(Long categoryId, Status status);
}
