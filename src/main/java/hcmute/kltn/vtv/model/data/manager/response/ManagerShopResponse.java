package hcmute.kltn.vtv.model.data.manager.response;

import hcmute.kltn.vtv.model.dto.vtv.ShopDTO;
import hcmute.kltn.vtv.model.entity.vendor.Shop;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerShopResponse extends ResponseAbstract {

    private ShopDTO shopDTO;

    public static ManagerShopResponse managerShopResponse(Shop shop, String message, String status) {
        ManagerShopResponse response = new ManagerShopResponse();
        response.setShopDTO(ShopDTO.convertEntityToDTO(shop));
        response.setMessage(message);
        response.setStatus(status);
        response.setCode(200);

        return response;
    }

}
