package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.user.request.CommentRequest;
import hcmute.kltn.vtv.model.data.user.response.CommentResponse;
import hcmute.kltn.vtv.model.dto.CommentDTO;
import hcmute.kltn.vtv.model.entity.vtc.Comment;
import hcmute.kltn.vtv.model.entity.vtc.Customer;
import hcmute.kltn.vtv.model.entity.vtc.Review;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.CommentRepository;
import hcmute.kltn.vtv.repository.ReviewRepository;
import hcmute.kltn.vtv.service.user.ICommentCustomerService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.service.user.IReviewCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentCustomerServiceImpl implements ICommentCustomerService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private final IReviewCustomerService reviewCustomerService;

    @Override
    @Transactional
    public CommentResponse addNewComment(CommentRequest request) {
        Customer customer = customerService.getCustomerByUsername(request.getUsername());
        Review review = reviewCustomerService.checkReviewRole(request.getReviewId(), request.getUsername(),
                request.isShop());
        String shopName = request.isShop() ? review.getProduct().getCategory().getShop().getName() : "";

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setReview(review);
        comment.setCustomer(customer);
        comment.setStatus(Status.ACTIVE);
        comment.setShopName(shopName);
        comment.setCreateDate(new Date());

        try {
            commentRepository.save(comment);
            return commentResponse(comment, request.getReviewId(), request.getUsername(),
                    "Bình luận thành công vào đánh giá thành công.");
        } catch (Exception e) {
            throw new BadRequestException("Bình luận thất bại.");
        }
    }

    @Override
    @Transactional
    public CommentResponse deleteComment(Long commentId, String username) {
        Comment comment = checkComment(commentId, username);

        comment.setStatus(Status.INACTIVE);
        try {
            commentRepository.save(comment);
            return commentResponse(comment, comment.getReview().getReviewId(), username, "Xóa bình luận thành công.");
        } catch (Exception e) {
            throw new BadRequestException("Xóa bình luận thất bại." + e.getMessage());
        }
    }

    private Comment checkComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException("Bình luận không tồn tại."));

        if (!comment.getCustomer().getUsername().equals(username)) {
            throw new BadRequestException("Bạn không phải chủ bình luận. Bạn không có quyền xóa bình luận này.");
        }

        if (comment.getStatus() == Status.INACTIVE) {
            throw new BadRequestException("Bình luận này đã bị xóa.");
        }

        return comment;
    }

    private CommentResponse commentResponse(Comment comment, Long reviewId, String username, String message) {
        CommentResponse response = new CommentResponse();
        response.setCommentDTO(CommentDTO.convertEntityToDTO(comment));
        response.setMessage(message);
        response.setCode(200);
        response.setUsername(username);
        response.setReviewId(reviewId);
        response.setStatus("success");
        return response;
    }

}
