package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.data.vendor.request.StatisticsRequest;
import hcmute.kltn.vtv.model.data.vendor.response.StatisticsResponse;

public interface IRevenueService {
    StatisticsResponse statisticsByDate(StatisticsRequest request);
}
