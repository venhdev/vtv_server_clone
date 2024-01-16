package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.user.ReviewDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse extends ResponseAbstract {

    private String username;
    private Long productId;
    private ReviewDTO reviewDTO;

}
