package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.model.data.user.request.MultipleOrderRequestWithCart;
import hcmute.kltn.vtv.model.data.user.request.OrderRequestWithCart;
import hcmute.kltn.vtv.model.data.user.response.MultipleOrderResponse;
import hcmute.kltn.vtv.model.data.user.response.OrderResponse;
import hcmute.kltn.vtv.model.entity.user.Cart;
import hcmute.kltn.vtv.repository.user.OrderRepository;
import hcmute.kltn.vtv.service.guest.IProductVariantService;
import hcmute.kltn.vtv.service.location.IDistanceLocationService;
import hcmute.kltn.vtv.service.shipping.IShippingService;
import hcmute.kltn.vtv.service.shipping.ITransportHandleService;
import hcmute.kltn.vtv.service.shipping.ITransportService;
import hcmute.kltn.vtv.service.user.*;
import hcmute.kltn.vtv.service.vtv.IMailService;
import hcmute.kltn.vtv.service.vtv.INotificationService;
import hcmute.kltn.vtv.service.wallet.ILoyaltyPointService;
import hcmute.kltn.vtv.service.wallet.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MultipleOrderServiceImpl implements IMultipleOrderService {

    private final OrderRepository orderRepository;
    private final IOrderService orderService;
    private final IOrderItemService orderItemService;
    private final IVoucherOrderService voucherOrderService;
    private final ICartService cartService;
    private final ICustomerService customerService;
    private final IAddressService addressService;
    private final IDistanceLocationService distanceLocationService;
    private final IProductVariantService productVariantService;
    private final IVoucherCustomerService voucherCustomerService;
    private final IShippingService shippingService;
    private final ILoyaltyPointService loyaltyPointService;
    private final IMailService mailService;
    private final INotificationService notificationService;
    private final ITransportService transportService;
    private final ITransportHandleService transportHandleService;
    private final IWalletService walletService;


    @Override
    public MultipleOrderResponse createMultipleOrderByCartIds(List<UUID> cartIds, String username) {
        cartService.checkDuplicateCartIds(cartIds);
        cartService.checkExistsCartIdsInUsername(cartIds, username);

        List<Cart> carts = cartService.getListCartByUsernameAndIds(username, cartIds);
        HashMap<Long, List<Cart>> mapShopIdToCart = cartService.groupCartByShopId(carts);
        List<OrderResponse> mapCreateOrderResponse = createOrderResponsesByMapCarts(mapShopIdToCart, username);

        return MultipleOrderResponse.multipleOrderResponse(mapCreateOrderResponse, "Tạo đơn nhiều đơn hàng thành công!", "OK");
    }


    @Override
    public MultipleOrderResponse createMultipleOrderByRequest(MultipleOrderRequestWithCart request, String username) {

        List<OrderResponse> mapCreateOrderResponse = createMultipleOrderResponsesByRequest(request, username);

        return MultipleOrderResponse.multipleOrderResponse(mapCreateOrderResponse, "Cập nhật tạo nhiều đơn hàng thành công!", "OK");
    }


    @Override
    public MultipleOrderResponse addNewMultipleOrderByRequest(MultipleOrderRequestWithCart request, String username) {
        List<OrderResponse> mapCreateOrderResponse = addNewMultipleOrderResponsesByRequest(request, username);

        return MultipleOrderResponse.multipleOrderResponse(mapCreateOrderResponse, "Đặt hàng nhiều đơn hàng thành công!", "Success");
    }


    private List<OrderResponse> addNewMultipleOrderResponsesByRequest(MultipleOrderRequestWithCart request, String username) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderRequestWithCart orderRequestWithCart : request.getOrderRequestWithCarts()) {
            orderResponses.add(orderService.addNewOrderWithCart(orderRequestWithCart, username));
        }

        return orderResponses;
    }


    private List<OrderResponse> createMultipleOrderResponsesByRequest(MultipleOrderRequestWithCart request, String username) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderRequestWithCart orderRequestWithCart : request.getOrderRequestWithCarts()) {
            orderResponses.add(orderService.createOrderWithCart(orderRequestWithCart, username));
        }

        return orderResponses;
    }


    private List<OrderResponse> createOrderResponsesByMapCarts(Map<Long, List<Cart>> mapShopIdToCart, String username) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Map.Entry<Long, List<Cart>> entry : mapShopIdToCart.entrySet()) {
            List<UUID> cartIds = entry.getValue().stream().map(Cart::getCartId).toList();
            orderResponses.add(orderService.createOrderByCartIds(username, cartIds));
        }

        return orderResponses;
    }


}
