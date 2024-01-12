package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.CartDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse extends ResponseAbstract {

    private String username;

    private CartDTO cartDTO;

}
