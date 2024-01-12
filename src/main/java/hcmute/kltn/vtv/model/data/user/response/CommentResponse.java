package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.CommentDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse extends ResponseAbstract {

    private String username;
    private Long reviewId;
    private CommentDTO commentDTO;
}
