package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.model.data.vtv.response.StatisticsCustomersResponse;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.user.CustomerRepository;
import hcmute.kltn.vtv.repository.user.OrderRepository;
import hcmute.kltn.vtv.repository.vendor.ProductRepository;
import hcmute.kltn.vtv.service.manager.IManagerRevenueService;
import hcmute.kltn.vtv.service.vendor.IShopService;
import hcmute.kltn.vtv.service.vtv.impl.DateServiceImpl;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ManagerRevenueServiceImpl implements IManagerRevenueService {


    private final CustomerRepository customerRepository;


    @Override
    public StatisticsCustomersResponse statisticsCustomersByDateAndStatus(Date startDate, Date endDate) {
        startDate = DateServiceImpl.formatStartOfDate(startDate);
        endDate = DateServiceImpl.formatEndOfDate(endDate);
        List<Customer> customers = customerRepository
                .findAllByStatusAndCreateAtBetween(Status.ACTIVE,
                        DateServiceImpl.convertDateToLocalDateTime(startDate),
                        DateServiceImpl.convertDateToLocalDateTime(endDate))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khách hàng nào."));
        String message = "Thống kê khách hàng từ ngày " + DateServiceImpl.formatStringDate(startDate)
                + " đến ngày " + DateServiceImpl.formatStringDate(endDate) + " thành công.";

        return StatisticsCustomersResponse.statisticsCustomersResponse(customers, startDate, endDate, message);
    }

}
