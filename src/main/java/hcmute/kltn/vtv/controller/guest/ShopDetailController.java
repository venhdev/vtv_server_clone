package hcmute.kltn.vtv.controller.guest;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.service.guest.IFollowedGuestService;
import hcmute.kltn.vtv.service.guest.IShopGuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ShopDetailController {

    private final IShopGuestService shopDetailService;
    private final IFollowedGuestService followedGuestService;

//    @GetMapping("/shop/{shopId}")
//    public ResponseEntity<ShopDetailResponse> getShopDetailByShopId(@PathVariable Long shopId,
//            HttpServletRequest request) {
//        if (shopId == null) {
//            throw new BadRequestException("Mã cửa hàng không được để trống!");
//        }
//        String username = (String) request.getAttribute("username");
//        return ResponseEntity.ok(shopDetailService.getShopDetailByShopId(shopId, username));
//    }

    @GetMapping("/count-followed/{shopId}")
    public ResponseEntity<Integer> countFollowedShop(@PathVariable Long shopId) {

        return ResponseEntity.ok(followedGuestService.countFollowedByShop(shopId));
    }
}
