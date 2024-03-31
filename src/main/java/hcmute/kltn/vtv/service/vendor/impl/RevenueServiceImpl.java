package hcmute.kltn.vtv.service.vendor.impl;

import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.service.vtv.impl.DateServiceImpl;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.vendor.response.StatisticsResponse;
import hcmute.kltn.vtv.model.dto.vtv.StatisticsDTO;
import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.entity.vendor.Shop;
import hcmute.kltn.vtv.repository.user.OrderRepository;
import hcmute.kltn.vtv.service.vendor.IRevenueService;
import hcmute.kltn.vtv.service.vendor.IShopService;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements IRevenueService {

    private final OrderRepository orderRepository;
    private final IShopService shopService;

    @Override
    public StatisticsResponse statisticsRevenueByDate(Date startDate, Date endDate, String username) {
        Shop shop = shopService.getShopByUsername(username);
        startDate = DateServiceImpl.formatStartOfDate(startDate);
        endDate = DateServiceImpl.formatEndOfDate(endDate);

        List<Order> orders = orderRepository
                .findAllByShopShopIdAndStatusAndOrderDateBetween(shop.getShopId(), OrderStatus.COMPLETED, startDate, endDate)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng nào."));
        String message = "Thống kê doanh thu từ ngày " + DateServiceImpl.formatStringDate(startDate) + " đến ngày " + DateServiceImpl.formatStringDate(endDate) + " thành công.";

        return StatisticsResponse.statisticsResponse(orders, startDate, endDate, message);
    }


}
