package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.OrderDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListOrderResponse extends ResponseAbstract {

    private String username;

    private int count;

    private List<OrderDTO> orderDTOs;

}
