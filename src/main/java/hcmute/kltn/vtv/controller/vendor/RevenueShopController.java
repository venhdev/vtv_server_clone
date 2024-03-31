package hcmute.kltn.vtv.controller.vendor;

import hcmute.kltn.vtv.model.data.vendor.response.StatisticsResponse;
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


    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statisticsRevenueByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                                      HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        dateService.checkDatesRequest(startDate, endDate, 31);
        return ResponseEntity.ok(revenueService.statisticsRevenueByDate(startDate, endDate, username));
    }

}
