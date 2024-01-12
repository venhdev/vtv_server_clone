package hcmute.kltn.vtv.repository;

import hcmute.kltn.vtv.model.entity.vtc.Comment;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findAllByReviewReviewIdAndStatus(Long reviewId, Status status);
}
