package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.request.ReviewRequest;
import hcmute.kltn.vtv.model.data.user.response.ReviewResponse;
import hcmute.kltn.vtv.model.entity.vtc.Review;

public interface IReviewCustomerService {
    ReviewResponse addNewReview(ReviewRequest request, String username);

    ReviewResponse getReviewByOrderItemId(Long orderItemId);

    ReviewResponse deleteReview(Long reviewId, String username);

    Review checkReviewRole(Long reviewId, String username, boolean isShop);

    Review checkReview(Long reviewId);
}
