package hcmute.kltn.vtv.model.data.paging.response;

import hcmute.kltn.vtv.model.dto.OrderDTO;
import hcmute.kltn.vtv.model.dto.ShopDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageOrderResponse extends ResponseAbstract {

    private int count;
    private int page;
    private int size;
    private int totalPage;
    private ShopDTO shopDTO;
    private List<OrderDTO> orderDTOs;
}
