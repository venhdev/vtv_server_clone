package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.request.RegisterShopRequest;
import hcmute.kltn.vtv.model.data.vendor.request.UpdateShopRequest;
import hcmute.kltn.vtv.model.data.vendor.response.ShopResponse;
import hcmute.kltn.vtv.model.entity.vtc.Shop;
import hcmute.kltn.vtv.model.extra.Status;

public interface IShopService {
    ShopResponse registerShop(RegisterShopRequest request);

    ShopResponse getProfileShop(String username);

    ShopResponse updateShop(UpdateShopRequest request);

    ShopResponse updateStatusShop(String username, Status status);

    Shop getShopByUsername(String username);
}
