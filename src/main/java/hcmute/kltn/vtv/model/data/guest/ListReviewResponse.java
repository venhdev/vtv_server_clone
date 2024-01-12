package hcmute.kltn.vtv.model.data.guest;

import hcmute.kltn.vtv.model.dto.ReviewDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListReviewResponse extends ResponseAbstract {

    private int count;
    private Long productId;
    private long averageRating;
    private List<ReviewDTO> reviewDTOs;

}
