package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.user.response.OrderItemResponse;
import hcmute.kltn.vtv.model.entity.vtc.*;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.CartRepository;
import hcmute.kltn.vtv.repository.OrderItemRepository;
import hcmute.kltn.vtv.repository.ProductRepository;
import hcmute.kltn.vtv.repository.ProductVariantRepository;
import hcmute.kltn.vtv.service.user.ICartService;
import hcmute.kltn.vtv.service.user.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private final OrderItemRepository orderItemRepository;
    @Autowired
    private final ICartService cartService;
    @Autowired
    private final ProductVariantRepository productVariantRepository;
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final ProductRepository productRepository;

    @Override
    public OrderItemResponse getOrderItemByOrderItemId(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new BadRequestException("Mã đơn hàng không tồn tại!"));

        OrderItemResponse response = new OrderItemResponse();
        response.setOrderItemId(orderItem.getOrderItemId());
        response.setOrderId(orderItem.getOrder().getOrderId());
        response.setCartId(orderItem.getCart().getCartId());
        response.setProductVariantId(orderItem.getCart().getProductVariant().getProductVariantId());
        response.setSku(orderItem.getCart().getProductVariant().getSku());
        response.setProductVariantImage(orderItem.getCart().getProductVariant().getImage());
        response.setQuantity(orderItem.getCart().getQuantity());
        response.setPrice(orderItem.getCart().getProductVariant().getPrice());
        response.setProductId(orderItem.getCart().getProductVariant().getProduct().getProductId());
        response.setProductName(orderItem.getCart().getProductVariant().getProduct().getName());
        response.setProductImage(orderItem.getCart().getProductVariant().getProduct().getImage());
        response.setShopId(orderItem.getCart().getProductVariant().getProduct().getCategory().getShop().getShopId());
        response.setShopName(orderItem.getCart().getProductVariant().getProduct().getCategory().getShop().getName());
        response.setCode(200);
        response.setMessage("Lấy thông tin chi tiết về sản phẩm trong đơn hàng thành công!");
        response.setStatus("OK");

        return response;
    }

    public OrderItem createOrderItem(Long cartId, String username) {
        OrderItem orderItem = new OrderItem();
        Cart cart = cartService.getCartByUserNameAndId(username, cartId);
        orderItem.setOrder(null);
        orderItem.setCart(cart);
        return orderItem;
    }

    @Override
    public List<OrderItem> createOrderItems(String username, List<Long> cartIds) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (Long cartId : cartIds) {
            orderItems.add(createOrderItem(cartId, username));
        }

        return orderItems;
    }

    @Transactional
    @Override
    public List<OrderItem> saveOrderItem(Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            Cart cart = orderItem.getCart();

            if (cart.getProductVariant().getStatus() == Status.DELETED ||
                    cart.getProductVariant().getProduct().getStatus() == Status.DELETED) {
                throw new BadRequestException("Sản phẩm đã bị xóa!");
            }

            cart.setStatus(Status.ORDER);
            cart.setUpdateAt(order.getUpdateAt());
            try {
                cartRepository.save(cart);
            } catch (Exception e) {
                throw new BadRequestException("Cập nhật trạng thái giỏ hàng thất bại!");
            }

            ProductVariant productVariant = cart.getProductVariant();

            productVariant.setQuantity(productVariant.getQuantity() - cart.getQuantity());
            try {
                productVariantRepository.save(productVariant);
            } catch (Exception e) {
                throw new BadRequestException("Cập nhật số lượng sản phẩm thất bại!");
            }

            Product product = productVariant.getProduct();
            product.setSold(product.getSold() + cart.getQuantity());
            try {
                productRepository.save(product);
            } catch (Exception e) {
                throw new BadRequestException("Cập nhật số lượng sản phẩm đã bán thất bại!");
            }

            orderItem.setOrder(order);
            try {
                OrderItem item = orderItemRepository.save(orderItem);
                orderItems.add(item);
            } catch (Exception e) {
                throw new BadRequestException("Cập nhật đơn hàng thất bại!");
            }

        }

        return orderItems;
    }

    @Transactional
    @Override
    public List<OrderItem> cancelOrderItem(Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            Cart cart = orderItem.getCart();
            cart.setStatus(Status.CANCEL);
            cart.setUpdateAt(order.getUpdateAt());
            try {

                ProductVariant productVariant = cart.getProductVariant();
                productVariant.setQuantity(productVariant.getQuantity() + cart.getQuantity());
                productVariantRepository.save(productVariant);

                cartRepository.save(cart);
                orderItems.add(orderItem);
            } catch (Exception e) {
                throw new BadRequestException("Cập nhật trạng thái giỏ hàng thất bại!");
            }

        }
        return orderItems;
    }
}
