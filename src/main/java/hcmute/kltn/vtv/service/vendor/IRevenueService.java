package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.response.ListProductResponse;
import hcmute.kltn.vtv.model.data.vendor.response.ListStatisticsOrderResponse;
import hcmute.kltn.vtv.model.extra.OrderStatus;

import java.util.Date;

public interface IRevenueService {


    ListStatisticsOrderResponse statisticsOrderByDateAndStatus(Date startDate, Date endDate, OrderStatus status, String username);

    ListProductResponse getTopProductByLimitAndDate(int limit, Date startDate, Date endDate, String username);
}
