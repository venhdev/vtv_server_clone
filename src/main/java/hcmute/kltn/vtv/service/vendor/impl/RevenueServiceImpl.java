package hcmute.kltn.vtv.service.vendor.impl;

import hcmute.kltn.vtv.model.data.vendor.response.ListProductResponse;
import hcmute.kltn.vtv.model.entity.vendor.Product;
import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.repository.vendor.ProductRepository;
import hcmute.kltn.vtv.service.vtv.impl.DateServiceImpl;
import hcmute.kltn.vtv.model.data.vendor.response.ListStatisticsOrderResponse;
import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.entity.vendor.Shop;
import hcmute.kltn.vtv.repository.user.OrderRepository;
import hcmute.kltn.vtv.service.vendor.IRevenueService;
import hcmute.kltn.vtv.service.vendor.IShopService;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements IRevenueService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final IShopService shopService;

    public ListStatisticsOrderResponse statisticsOrderByDateAndStatus(Date startDate, Date endDate, OrderStatus status, String username) {
        Shop shop = shopService.getShopByUsername(username);
        startDate = DateServiceImpl.formatStartOfDate(startDate);
        endDate = DateServiceImpl.formatEndOfDate(endDate);
        List<Order> orders = orderRepository
                .findAllByShopShopIdAndStatusAndOrderDateBetween(shop.getShopId(), status, startDate, endDate)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng nào."));
        String message = "Thống kê " + messageByStatus(status) + " từ ngày " + DateServiceImpl.formatStringDate(startDate)
                + " đến ngày " + DateServiceImpl.formatStringDate(endDate) + " thành công.";

        return ListStatisticsOrderResponse.listStatisticsResponse(orders, startDate, endDate, message);
    }


    @Override
    public ListProductResponse getTopProductByLimitAndDate(int limit, Date startDate, Date endDate, String username) {
        Shop shop = shopService.getShopByUsername(username);
        startDate = DateServiceImpl.formatStartOfDate(startDate);
        endDate = DateServiceImpl.formatEndOfDate(endDate);
        List<Product> productsBestSeller = productRepository.getBestProductsByLimitAndShopIdAndOrderStatusAndOrderDateBetween(
                        limit, shop.getShopId(), OrderStatus.COMPLETED.toString(), startDate, endDate)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách sản phẩm bán chạy nào."));



        String message = "Danh sách sản phẩm bán chạy của cửa hàng từ ngày " + DateServiceImpl.formatStringDate(startDate)
                + " đến ngày " + DateServiceImpl.formatStringDate(endDate) + " thành công.";

        return ListProductResponse.listProductResponseSort(productsBestSeller, message, "OK", "sold");
    }


    public static Map<Date, List<Order>> getOrdersByDate(List<Order> orders, Date startDate, Date endDate) {
        Map<Date, List<Order>> ordersByDate = new HashMap<>();
        List<Date> datesBetween = DateServiceImpl.getDatesBetween(startDate, endDate);

        for (Date date : datesBetween) {
            List<Order> ordersSameDate = new ArrayList<>();
            Date start = DateServiceImpl.formatStartOfDate(date);
            Date end = DateServiceImpl.formatEndOfDate(date);

            for (Order checkOrder : orders) {
                Date dateUpdate = DateServiceImpl.convertToLocalDateTimeToDate(checkOrder.getUpdateAt());
                if (dateUpdate.after(start) && dateUpdate.before(end)) {
                    ordersSameDate.add(checkOrder);
                }
            }
            ordersByDate.put(start, ordersSameDate);
        }

        for (List<Order> ordersOnDate : ordersByDate.values()) {
            ordersOnDate.sort(Comparator.comparing(Order::getOrderDate));
        }

        return ordersByDate;
    }


    private String messageByStatus(OrderStatus status) {
        return switch (status) {
            case COMPLETED -> "doanh thu";
            case DELIVERED -> "đơn hàng đã giao";
            case SHIPPING -> "đơn hàng đang giao";
            case CANCEL -> "đơn hàng đã hủy";
            default -> "đơn hàng";
        };
    }


}
