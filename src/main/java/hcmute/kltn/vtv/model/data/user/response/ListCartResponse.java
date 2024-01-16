package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.user.ListCartByShopDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListCartResponse extends ResponseAbstract {

    private String username;

    int count;

    private List<ListCartByShopDTO> listCartByShopDTOs;
}
