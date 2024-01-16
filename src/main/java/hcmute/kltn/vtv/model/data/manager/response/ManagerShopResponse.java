package hcmute.kltn.vtv.model.data.manager.response;

import hcmute.kltn.vtv.model.dto.vtv.ShopDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerShopResponse extends ResponseAbstract {

    private ShopDTO shopDTO;

}
