package hcmute.kltn.vtv.model.dto.vtv;

import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.service.vtv.impl.DateServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticsDTO {

    private Long totalMoney;
    private int totalOrder;
    private int totalProduct;
    private Date date;

    public static StatisticsDTO convertOrdersAndDateToDTO(List<Order> ordersOnDate, Date orderDate) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setDate(orderDate);
        statisticsDTO.setTotalMoney(totalMoney(ordersOnDate));
        statisticsDTO.setTotalOrder(ordersOnDate.size());
        statisticsDTO.setTotalProduct(totalProduct(ordersOnDate));

        return statisticsDTO;
    }


    public static Long totalMoney(List<Order> orders) {
        long totalMoney = 0;
        for (Order order : orders) {
            totalMoney += (long) (order.getTotalPrice() + order.getDiscountShop() - order.getTotalPrice() * 0.04);
        }
        return totalMoney;
    }

    public static int totalProduct(List<Order> orders) {
        int totalProduct = 0;
        for (Order order : orders) {
            totalProduct += order.getOrderItems().size();
        }
        return totalProduct;
    }




    public static  List<StatisticsDTO> covertStatisticsDTOs(List<Order> orders, Date startDate, Date endDate) {
        List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
        Map<Date, List<Order>> ordersByDate = getOrdersByDate(orders, startDate, endDate);
        for (Map.Entry<Date, List<Order>> entry : ordersByDate.entrySet()) {
            Date orderDate = entry.getKey();
            List<Order> ordersOnDate = entry.getValue();
            statisticsDTOs.add(StatisticsDTO.convertOrdersAndDateToDTO(ordersOnDate, orderDate));
        }
        statisticsDTOs.sort(Comparator.comparing(StatisticsDTO::getDate));

        return statisticsDTOs;
    }

    public static  Map<Date, List<Order>> getOrdersByDate(List<Order> orders, Date startDate, Date endDate) {
        Map<Date, List<Order>> ordersByDate = new HashMap<>();
        List<Date> datesBetween = getDatesBetween(startDate, endDate);

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

    public static   List<Date> getDatesBetween(Date startDate, Date endDate) {
        startDate = DateServiceImpl.formatStartOfDate(startDate);
        endDate = DateServiceImpl.formatEndOfDate(endDate);
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return datesInRange;
    }


}
