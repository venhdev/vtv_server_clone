package hcmute.kltn.vtv.controller.shpping;


import hcmute.kltn.vtv.model.data.shipping.response.DeliverResponse;
import hcmute.kltn.vtv.service.shipping.IDeliverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shipping/deliver")
@RequiredArgsConstructor
public class DeliverController {

    private final IDeliverService deliverService;

    @GetMapping("/info")
    public ResponseEntity<DeliverResponse> getDeliverInfo(HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(deliverService.getDeliverResponseByUsername(username));
    }
}
