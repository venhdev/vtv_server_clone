package hcmute.kltn.vtv.service.vendor.impl;

import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.service.vtv.impl.DateServiceImpl;
import hcmute.kltn.vtv.model.data.vendor.response.ListStatisticsResponse;
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
    private final IShopService shopService;

    @Override
    public ListStatisticsResponse statisticsRevenueByDate(Date startDate, Date endDate, String username) {
        Shop shop = shopService.getShopByUsername(username);
        startDate = DateServiceImpl.formatStartOfDate(startDate);
        endDate = DateServiceImpl.formatEndOfDate(endDate);

        List<Order> orders = orderRepository
                .findAllByShopShopIdAndStatusAndOrderDateBetween(shop.getShopId(), OrderStatus.COMPLETED, startDate, endDate)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng nào."));
        String message = "Thống kê doanh thu từ ngày " + DateServiceImpl.formatStringDate(startDate) + " đến ngày " + DateServiceImpl.formatStringDate(endDate) + " thành công.";

        return ListStatisticsResponse.listStatisticsResponse(orders, startDate, endDate, message);
    }



    public static  Map<Date, List<Order>> getOrdersByDate(List<Order> orders, Date startDate, Date endDate) {
        Map<Date, List<Order>> ordersByDate = new HashMap<>();
        List<Date> datesBetween = DateServiceImpl.getDatesBetween(startDate, endDate);

        for (Date date : datesBetween) {
            List<Order> ordersSameDate = new ArrayList<>();
            Date start = DateServiceImpl.formatStartOfDate(date);
            Date end = DateServiceImpl.formatEndOfDate(date);

            if (!orders.isEmpty()) {
                for (Order checkOrder : orders) {
                    Date dateUpdate = DateServiceImpl.convertToLocalDateTimeToDate(checkOrder.getUpdateAt());
                    if (dateUpdate.after(start) && dateUpdate.before(end)) {
                        ordersSameDate.add(checkOrder);
                    }
                }
                ordersSameDate.sort(Comparator.comparing(Order::getOrderDate));
            }
            ordersByDate.put(start, ordersSameDate);
        }

        return ordersByDate;
    }


}
