package hcmute.kltn.vtv.model.data.guest;

import hcmute.kltn.vtv.model.dto.vtv.CategoryDTO;
import hcmute.kltn.vtv.model.dto.vendor.ProductDTO;
import hcmute.kltn.vtv.model.dto.vtv.ShopDTO;
import hcmute.kltn.vtv.model.entity.vendor.Product;
import hcmute.kltn.vtv.model.entity.vendor.Shop;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetailResponse extends ResponseAbstract {

    private ShopDTO shopDTO;

    private int countFollowed;

    private int countProduct;

    private int countCategoryShop;

    private float averageRatingShop;



    public static ShopDetailResponse shopDetailResponse(Shop shop, int countFollowed, int countProduct,
                                                        int countCategoryShop, float averageRatingShop,
                                                        String message, String status) {
        ShopDetailResponse shopDetailResponse = new ShopDetailResponse();
        shopDetailResponse.setShopDTO(ShopDTO.convertEntityToDTO(shop));
        shopDetailResponse.setCountFollowed(countFollowed);
        shopDetailResponse.setCountProduct(countProduct);
        shopDetailResponse.setCountCategoryShop(countCategoryShop);
        shopDetailResponse.setAverageRatingShop(averageRatingShop);
        shopDetailResponse.setMessage(message);
        shopDetailResponse.setStatus(status);
        shopDetailResponse.setCode(200);


        return shopDetailResponse;
    }


}
