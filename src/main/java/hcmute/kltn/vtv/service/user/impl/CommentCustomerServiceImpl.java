package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.repository.user.OrderRepository;
import hcmute.kltn.vtv.service.user.IOrderItemService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.user.request.CommentRequest;
import hcmute.kltn.vtv.model.data.user.response.CommentResponse;
import hcmute.kltn.vtv.model.entity.user.Comment;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.entity.user.Review;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.user.CommentRepository;
import hcmute.kltn.vtv.service.user.ICommentCustomerService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.service.user.IReviewCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentCustomerServiceImpl implements ICommentCustomerService {


    private final CommentRepository commentRepository;
    private final ICustomerService customerService;
    private final IReviewCustomerService reviewCustomerService;
    private final OrderRepository orderRepository;
    private final IOrderItemService orderItemService;


    @Override
    @Transactional
    public CommentResponse addNewCommentByCustomer(CommentRequest request) {
        Comment comment = createCommentByCommentRequest(request);
        checkTimeComment(request.getReviewId());
        try {
            commentRepository.save(comment);

            return CommentResponse.commentResponse(comment, request.getReviewId(), request.getUsername(),
                    "Bình luận thành công vào đánh giá thành công.", "Success");
        } catch (Exception e) {
            throw new BadRequestException("Bình luận thất bại.");
        }
    }

    @Override
    @Transactional
    public CommentResponse deleteComment(UUID commentId, String username) {
        Comment comment = checkComment(commentId, username);
        comment.setStatus(Status.INACTIVE);
        try {
            commentRepository.save(comment);

            return CommentResponse.commentResponse(comment, comment.getReview().getReviewId(),
                    username, "Xóa bình luận thành công.", "Success");
        } catch (Exception e) {
            throw new BadRequestException("Xóa bình luận thất bại." + e.getMessage());
        }
    }

    private Comment checkComment(UUID commentId, String username) {
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


    private Comment createCommentByCommentRequest(CommentRequest request) {
        Customer customer = customerService.getCustomerByUsername(request.getUsername());
        Review review = reviewCustomerService.checkReviewRole(request.getReviewId(), request.getUsername(),
                request.isShop());
        String shopName = request.isShop() ? review.getProduct().getShop().getName() : "";

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setReview(review);
        comment.setCustomer(customer);
        comment.setStatus(Status.ACTIVE);
        comment.setShopName(shopName);
        comment.setCreateDate(new Date());
        return comment;
    }


    private void checkTimeComment(UUID reviewId) {
        UUID orderId = reviewCustomerService.checkReview(reviewId).getOrderItem().getOrder().getOrderId();

        if (!orderRepository.existsByOrderIdAndUpdateAtAfterAndStatus(orderId, LocalDateTime.now().minusDays(7), OrderStatus.COMPLETED)) {
            throw new BadRequestException("Đã hết thời gian bình luận sản phẩm này! Thời gian bình luận chỉ trong vòng 7 ngày sau khi đơn hàng hoàn thành.");
        }
    }


}
