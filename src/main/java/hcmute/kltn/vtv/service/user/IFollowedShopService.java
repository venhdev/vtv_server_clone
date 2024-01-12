package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.response.FollowedShopResponse;
import hcmute.kltn.vtv.model.data.user.response.ListFollowedShopResponse;

public interface IFollowedShopService {
   FollowedShopResponse addNewFollowedShop(Long shopId, String username);

   ListFollowedShopResponse getListFollowedShopByUsername(String username);

   FollowedShopResponse deleteFollowedShop(Long followedShopId, String username);
}
