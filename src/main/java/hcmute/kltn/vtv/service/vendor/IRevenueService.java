package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.response.ListStatisticsResponse;
import hcmute.kltn.vtv.model.extra.OrderStatus;

import java.util.Date;

public interface IRevenueService {


    ListStatisticsResponse statisticsRevenueByDateAndStatus(Date startDate, Date endDate, OrderStatus status, String username);
}
