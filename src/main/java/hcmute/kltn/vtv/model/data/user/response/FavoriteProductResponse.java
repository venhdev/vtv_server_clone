package hcmute.kltn.vtv.model.data.user.response;

import hcmute.tlcn.vtc.model.dto.FavoriteProductDTO;
import hcmute.tlcn.vtc.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProductResponse extends ResponseAbstract {

    private FavoriteProductDTO favoriteProductDTO;


}
