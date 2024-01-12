package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.FollowedShopDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FollowedShopResponse extends ResponseAbstract {

    private FollowedShopDTO followedShopDTO;
}
