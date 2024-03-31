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
public class ListStatisticsResponse extends ResponseAbstract {

    private int count;
    private int totalOrder;
    private Long totalMoney;
    private Date dateStart;
    private Date dateEnd;
    private List<StatisticsDTO> statisticsDTOs;


    public static ListStatisticsResponse listStatisticsResponse(List<Order> orders, Date startDate, Date endDate, String message) {
        ListStatisticsResponse response = new ListStatisticsResponse();
        response.setStatisticsDTOs(StatisticsDTO.covertStatisticsDTOs(orders, startDate, endDate));
        response.setCount(response.getStatisticsDTOs().size());
        response.setTotalOrder(orders.size());
        response.setTotalMoney(totalMoney(orders));
        response.setDateStart(DateServiceImpl.formatStartOfDate(startDate));
        response.setDateEnd(DateServiceImpl.formatStartOfDate(endDate));
        response.setMessage(message);
        response.setCode(200);
        response.setStatus("OK");

        return response;
    }


    public static Long totalMoney(List<Order> orders) {
        long totalMoney = 0;
        for (Order order : orders) {
            totalMoney += (long) (order.getTotalPrice() + order.getDiscountShop() - order.getTotalPrice() * 0.04);
        }
        return totalMoney;
    }




}
