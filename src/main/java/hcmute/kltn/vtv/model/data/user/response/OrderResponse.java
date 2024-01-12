package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.OrderDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse extends ResponseAbstract {

    private String username;

    private OrderDTO orderDTO;

}
