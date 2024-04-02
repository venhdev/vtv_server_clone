package hcmute.kltn.vtv.service.manager;

import hcmute.kltn.vtv.model.data.vtv.response.StatisticsCustomersResponse;

import java.util.Date;

public interface IManagerRevenueService {
    StatisticsCustomersResponse statisticsCustomersByDateAndStatus(Date startDate, Date endDate);
}
