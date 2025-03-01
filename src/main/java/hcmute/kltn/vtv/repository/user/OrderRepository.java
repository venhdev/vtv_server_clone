package hcmute.kltn.vtv.repository.user;

import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    boolean existsByOrderId(UUID orderId);

    boolean existsByOrderIdAndShopShopId(UUID orderId, Long shopId);

    // check updateAt after date
    boolean existsByOrderIdAndUpdateAtAfterAndStatus(UUID orderId, LocalDateTime updateAt, OrderStatus status);

    boolean existsByOrderIdAndCustomerUsername(UUID orderId, String username);

    boolean existsByOrderIdAndStatus(UUID orderId,  OrderStatus status);


    Optional<Order> findByOrderId(UUID orderId);

    Optional<List<Order>> findAllByStatus(OrderStatus status);

    Optional<List<Order>> findAllByCustomerUsername(String username);

    Optional<List<Order>> findAllByCustomerUsernameAndStatus(String username, OrderStatus status);


    Optional<Order> findByOrderIdAndShopShopId(UUID orderId, Long shopId);

    Optional<Order> findByOrderIdAndCustomerUsername(UUID orderId, String username);

    Optional<List<Order>> findAllByShopShopId(Long shopId);

    Optional<List<Order>> findAllByShopShopIdAndStatus(Long shopId, OrderStatus status);

    Optional<List<Order>> findAllByShopShopIdAndOrderDateBetween(Long shopId, Date startOrderDate, Date endOrderDate);

    Optional<List<Order>> findAllByShopShopIdAndOrderDateBetweenAndStatus(Long shopId, Date startOrderDate,
            Date endOrderDate, OrderStatus status);


    Optional<List<Order>> findAllByShopShopIdAndStatusAndOrderDateBetween(Long shopId, OrderStatus status, Date startDate,
            Date endDate);

    Optional<Page<Order>> findAllByShopShopIdOrderByCreateAtDesc(Long shopId, PageRequest pageRequest);

    Optional<Page<Order>> findAllByShopShopIdAndStatusOrderByCreateAtDesc(Long shopId, OrderStatus status,
            PageRequest pageRequest);


}
