package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.request.OrderRequestWithCart;
import hcmute.kltn.vtv.model.data.user.request.OrderRequestWithProductVariant;
import hcmute.kltn.vtv.model.data.user.response.ListOrderResponse;
import hcmute.kltn.vtv.model.data.user.response.OrderResponse;
import hcmute.kltn.vtv.model.extra.OrderStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IOrderService {


    @Transactional
    OrderResponse createOrderByCartIds(String username, List<UUID> cartIds);
    @Transactional
    OrderResponse createOrderByMapProductVariantsAndQuantities(String username, Map<Long, Integer> productVariantIdsAndQuantities);
    @Transactional
    OrderResponse createOrderWithProductVariant(OrderRequestWithProductVariant request, String username);
    @Transactional
    OrderResponse createOrderWithCart(OrderRequestWithCart request, String username);

    @Transactional
    OrderResponse addNewOrderWithCart(OrderRequestWithCart request, String username);


    @Transactional
    OrderResponse addNewOrderWithProductVariant(OrderRequestWithProductVariant request, String username);

    ListOrderResponse getOrders(String username);

    ListOrderResponse getOrdersByStatus(String username, OrderStatus status);

    OrderResponse getOrderDetail(String username, UUID orderId);


    //
    @Transactional
    OrderResponse completeOrderById(String username, UUID orderId);

    @Transactional
    OrderResponse cancelOrderById(String username, UUID orderId);

    @Transactional
    OrderResponse returnOrderById(String username, UUID orderId, String reason);

    String messageByOrderStatus(OrderStatus status);


    Long getTotalPaymentByOrderId(UUID orderId, String username);
}
