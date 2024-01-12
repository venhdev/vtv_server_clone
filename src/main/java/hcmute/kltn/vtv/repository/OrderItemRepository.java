package hcmute.kltn.vtv.repository;

import hcmute.kltn.vtv.model.entity.vtc.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    boolean existsByOrderItemIdAndCartCustomerUsername(Long orderItemId, String username);

    Optional<List<OrderItem>> findAllByOrderOrderId(Long orderId);

}
