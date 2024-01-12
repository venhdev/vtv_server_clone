package hcmute.kltn.vtv.model.data.vendor.response;

import hcmute.kltn.vtv.model.dto.CustomerDTO;
import hcmute.kltn.vtv.model.dto.ShopDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopResponse extends ResponseAbstract {

    private ShopDTO shopDTO;
    private CustomerDTO customerDTO;
}
