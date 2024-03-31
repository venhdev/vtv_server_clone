package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.response.ListStatisticsResponse;

import java.util.Date;

public interface IRevenueService {


    ListStatisticsResponse statisticsRevenueByDate(Date startDate, Date endDate, String username);
}
