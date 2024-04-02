package hcmute.kltn.vtv.controller.manager;


import hcmute.kltn.vtv.model.data.vtv.response.StatisticsCustomersResponse;
import hcmute.kltn.vtv.service.manager.IManagerRevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/manager/revenue")
@RequiredArgsConstructor
public class ManagerRevenueController {

    private final IManagerRevenueService managerRevenueService;


    @GetMapping("/statistics/customers")
    public ResponseEntity<StatisticsCustomersResponse> statisticsCustomersByDateAndStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(managerRevenueService.statisticsCustomersByDateAndStatus(startDate, endDate));
    }


}
