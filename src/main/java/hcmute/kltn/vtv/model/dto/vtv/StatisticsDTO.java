package hcmute.kltn.vtv.model.dto.vtv;

import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.service.vendor.impl.RevenueServiceImpl;
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

    public static StatisticsDTO convertOrdersAndDateToDTO(List<Order> orders, Date date) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setDate(date);
        statisticsDTO.setTotalMoney(totalMoney(orders));
        statisticsDTO.setTotalOrder(orders.size());
        statisticsDTO.setTotalProduct(orders.stream().mapToInt(Order::getCount).sum());

        return statisticsDTO;
    }




    public static List<StatisticsDTO> covertStatisticsDTOs(List<Order> orders, Date startDate, Date endDate) {
        List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
        Map<Date, List<Order>> ordersByDate = RevenueServiceImpl.getOrdersByDate(orders, startDate, endDate);
        for (Map.Entry<Date, List<Order>> entry : ordersByDate.entrySet()) {
            Date date = entry.getKey();
            List<Order> ordersOnDate = entry.getValue();
            statisticsDTOs.add(StatisticsDTO.convertOrdersAndDateToDTO(ordersOnDate, date));
        }
        statisticsDTOs.sort(Comparator.comparing(StatisticsDTO::getDate));

        return statisticsDTOs;
    }


    public static Long totalMoney(List<Order> orders) {
        return orders.stream().mapToLong(order ->
                (long) (order.getTotalPrice() + order.getDiscountShop() - order.getTotalPrice() * 0.04)).sum();
    }



}
