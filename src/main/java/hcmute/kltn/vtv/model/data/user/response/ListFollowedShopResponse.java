package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.user.FollowedShopDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListFollowedShopResponse extends ResponseAbstract {

    private int count;
    private List<FollowedShopDTO> followedShopDTOs;
}
