package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.CustomerDTO;
import hcmute.kltn.vtv.model.dto.FavoriteProductDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListFavoriteProductResponse extends ResponseAbstract {

    int count;

    private CustomerDTO customerDTO;

    private List<FavoriteProductDTO> favoriteProductDTOs;

}
