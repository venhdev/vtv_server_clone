package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.response.StatisticsResponse;

import java.util.Date;

public interface IRevenueService {


    StatisticsResponse statisticsRevenueByDate(Date startDate, Date endDate, String username);
}
