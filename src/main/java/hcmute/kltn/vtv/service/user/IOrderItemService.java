package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.response.OrderItemResponse;
import hcmute.kltn.vtv.model.entity.vtc.Order;
import hcmute.kltn.vtv.model.entity.vtc.OrderItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderItemService {
    OrderItemResponse getOrderItemByOrderItemId(Long orderItemId);

    List<OrderItem> createOrderItems(String username, List<Long> cartIds);

    @Transactional
    List<OrderItem> saveOrderItem(Order order);

    @Transactional
    List<OrderItem> cancelOrderItem(Order order);
}
