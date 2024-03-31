package hcmute.kltn.vtv.model.data.vendor.response;

import hcmute.kltn.vtv.model.dto.vtv.StatisticsDTO;
import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import hcmute.kltn.vtv.service.vtv.impl.DateServiceImpl;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponse extends ResponseAbstract {

    private int count;
    private int totalOrder;
    private Long totalMoney;
    private Date dateStart;
    private Date dateEnd;

    private List<StatisticsDTO> statisticsDTOs;


    public static StatisticsResponse statisticsResponse(List<Order> orders, Date startDate, Date endDate, String message) {
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        statisticsResponse.setStatisticsDTOs(StatisticsDTO.covertStatisticsDTOs(orders, startDate, endDate));
        statisticsResponse.setCount(statisticsResponse.getStatisticsDTOs().size());
        statisticsResponse.setTotalOrder(orders.size());
        statisticsResponse.setTotalMoney(totalMoney(orders));
        statisticsResponse.setDateStart(DateServiceImpl.formatStartOfDate(startDate));
        statisticsResponse.setDateEnd(DateServiceImpl.formatStartOfDate(endDate));
        statisticsResponse.setMessage(message);
        statisticsResponse.setCode(200);
        statisticsResponse.setStatus("OK");

        return statisticsResponse;
    }


    public static Long totalMoney(List<Order> orders) {
        long totalMoney = 0;
        for (Order order : orders) {
            totalMoney += (long) (order.getTotalPrice() + order.getDiscountShop() - order.getTotalPrice() * 0.04);
        }
        return totalMoney;
    }




}
