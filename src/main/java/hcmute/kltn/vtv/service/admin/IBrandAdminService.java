package hcmute.kltn.vtv.service.admin;

import hcmute.kltn.vtv.model.data.admin.request.BrandAdminRequest;
import hcmute.kltn.vtv.model.data.admin.response.AllBrandAdminResponse;
import hcmute.kltn.vtv.model.data.admin.response.BrandAdminResponse;
import hcmute.kltn.vtv.model.extra.Status;

public interface IBrandAdminService {
    BrandAdminResponse addNewBrand(BrandAdminRequest request);

    BrandAdminResponse getBrandById(Long brandId);

    AllBrandAdminResponse getAllBrandAdmin();

    BrandAdminResponse updateBrandAdmin(BrandAdminRequest request);

    BrandAdminResponse updateStatusBrandAdmin(Long brandId, String username, Status status);
}
