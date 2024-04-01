package hcmute.kltn.vtv.model.data.vtv.response;

import hcmute.kltn.vtv.model.dto.vtv.StatisticsOrderDTO;
import hcmute.kltn.vtv.model.dto.vtv.StatisticsProductDTO;
import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.entity.vendor.Product;
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
public class ListStatisticsProductResponse extends ResponseAbstract {

    private int count;
    private int totalOrder;
    private Long totalMoney;
    private Long totalSold;
    private Date dateStart;
    private Date dateEnd;
    private List<StatisticsProductDTO> statisticsProductDTOS;


    public static ListStatisticsProductResponse listStatisticsProductResponse(List<Product> products, List<Order> orders, Date startDate, Date endDate, String message) {
        ListStatisticsProductResponse response = new ListStatisticsProductResponse();
        response.setStatisticsProductDTOS(StatisticsProductDTO.convertProductsAndOrdersToDTOs(products, orders));
        response.setCount(products.size());
        response.setTotalOrder(orders.size());
        response.setTotalMoney(response.getStatisticsProductDTOS().stream().mapToLong(StatisticsProductDTO::getTotalMoney).sum());
        response.setTotalSold(response.getStatisticsProductDTOS().stream().mapToLong(StatisticsProductDTO::getTotalSold).sum());
        response.setDateStart(DateServiceImpl.formatStartOfDate(startDate));
        response.setDateEnd(DateServiceImpl.formatStartOfDate(endDate));
        response.setMessage(message);
        response.setCode(200);
        response.setStatus("OK");

        return response;
    }



}
