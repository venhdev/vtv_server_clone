package hcmute.kltn.vtv.service.guest.impl;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.guest.ListCommentResponse;
import hcmute.kltn.vtv.model.dto.CommentDTO;
import hcmute.kltn.vtv.model.entity.vtc.Comment;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.CommentRepository;
import hcmute.kltn.vtv.service.guest.ICommentService;
import hcmute.kltn.vtv.service.user.IReviewCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private final IReviewCustomerService reviewCustomerService;

    @Override
    public List<CommentDTO> getListCommentDTO(Long reviewId) {
        reviewCustomerService.checkReview(reviewId);

        List<Comment> comments = commentRepository.findAllByReviewReviewIdAndStatus(reviewId, Status.ACTIVE)
                .orElseThrow(() -> new BadRequestException("Không có bình luận nào cho đánh giá này."));

        // System.out.println("CommentDTO.convertEntitiesToDTOs(comments) = " +
        // CommentDTO.convertEntitiesToDTOs(comments));

        return CommentDTO.convertEntitiesToDTOs(comments);
    }

    @Override
    public ListCommentResponse getComments(Long reviewId) {
        reviewCustomerService.checkReview(reviewId);

        List<Comment> comments = commentRepository.findAllByReviewReviewIdAndStatus(reviewId, Status.ACTIVE)
                .orElseThrow(() -> new BadRequestException("Không có bình luận nào cho đánh giá này."));

        return listCommentResponse(comments, reviewId);
    }

    private ListCommentResponse listCommentResponse(List<Comment> comments, Long reviewId) {
        ListCommentResponse response = new ListCommentResponse();
        response.setCommentDTOs(CommentDTO.convertEntitiesToDTOs(comments));

        System.out
                .println("CommentDTO.convertEntitiesToDTOs(comments) = " + CommentDTO.convertEntitiesToDTOs(comments));

        response.setCount(comments.size());
        System.out.println("comments.size() = " + comments.size());
        response.setReviewId(reviewId);
        System.out.println("reviewId = " + reviewId);
        response.setMessage("Lấy danh sách bình luận của đánh giá thành công.");
        response.setCode(200);
        response.setStatus("OK");
        return response;
    }

}
