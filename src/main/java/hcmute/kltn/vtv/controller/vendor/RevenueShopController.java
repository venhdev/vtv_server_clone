package hcmute.kltn.vtv.controller.vendor;

import hcmute.kltn.vtv.model.data.vendor.request.StatisticsRequest;
import hcmute.kltn.vtv.model.data.vendor.response.StatisticsResponse;
import hcmute.kltn.vtv.service.vendor.IRevenueService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor/shop/revenue")
@RequiredArgsConstructor
public class RevenueShopController {

    @Autowired
    private IRevenueService revenueService;

    @PostMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statisticsByDate(@RequestBody StatisticsRequest request,
            HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getAttribute("username");
        request.setUsername(username);
        request.validate();
        return ResponseEntity.ok(revenueService.statisticsByDate(request));
    }

}
