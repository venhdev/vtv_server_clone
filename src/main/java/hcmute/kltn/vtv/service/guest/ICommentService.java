package hcmute.kltn.vtv.service.guest;

import hcmute.kltn.vtv.model.data.guest.ListCommentResponse;
import hcmute.kltn.vtv.model.dto.user.CommentDTO;

import java.util.List;

public interface ICommentService {

    List<CommentDTO> getListCommentDTO(Long reviewId);

    ListCommentResponse getComments(Long reviewId);
}
