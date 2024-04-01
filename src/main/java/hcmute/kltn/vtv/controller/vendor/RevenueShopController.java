package hcmute.kltn.vtv.controller.vendor;

import hcmute.kltn.vtv.model.data.vendor.response.ListProductResponse;
import hcmute.kltn.vtv.model.data.vtv.response.ListStatisticsOrderResponse;
import hcmute.kltn.vtv.model.data.vtv.response.ListStatisticsProductResponse;
import hcmute.kltn.vtv.model.extra.OrderStatus;
import hcmute.kltn.vtv.service.vendor.IRevenueService;
import hcmute.kltn.vtv.service.vtv.IDateService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/vendor/shop/revenue")
@RequiredArgsConstructor
public class RevenueShopController {

    private final IRevenueService revenueService;
    private final IDateService dateService;


    @GetMapping("/statistics/status/{status}")
    public ResponseEntity<ListStatisticsOrderResponse> statisticsOrderByDate(@PathVariable OrderStatus status,
                                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                                             HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        dateService.checkDatesRequest(startDate, endDate, 31);
        if (!status.equals(OrderStatus.COMPLETED) && !status.equals(OrderStatus.DELIVERED) &&
                !status.equals(OrderStatus.SHIPPING) && !status.equals(OrderStatus.CANCEL)) {
            throw new BadRequestException("Trạng thái thống kê không hợp lệ. Chỉ hỗ trợ COMPLETED, DELIVERED, SHIPPING, CANCEL.");
        }

        return ResponseEntity.ok(revenueService.statisticsOrderByDateAndStatus(startDate, endDate, status, username));
    }


    @GetMapping("/statistics/product/top/{limit}")
    public ResponseEntity<ListStatisticsProductResponse> statisticsTopProductByDate(@PathVariable int limit,
                                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                                                    HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        dateService.checkDatesRequest(startDate, endDate, 31);

        return ResponseEntity.ok(revenueService.getTopProductByLimitAndDate(limit, startDate, endDate, username));
    }

}
