package hcmute.kltn.vtv.vnpay;


import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.vnpay.model.VNPayDTO;
import hcmute.kltn.vtv.vnpay.model.VNPayResponse;
import hcmute.kltn.vtv.vnpay.service.impl.IOrderVNPayService;
import hcmute.kltn.vtv.vnpay.service.impl.IVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vnpay")
@RequiredArgsConstructor
public class VNPayController {

    private final IVNPayService vnPayService;
    private final IOrderVNPayService orderVNPayService;

    @PostMapping("/create-payment/{orderId}")
    public ResponseEntity<VNPayResponse> createPaymentByVNPay(@PathVariable UUID orderId, HttpServletRequest req) {
        String ipAddress = VNPayConfig.getIpAddress(req);
        String username = (String) req.getAttribute("username");

        return ResponseEntity.ok(vnPayService.createPaymentByVNPay(orderId, ipAddress, username));
    }

    @GetMapping("/return")
    public ResponseEntity<VNPayDTO> returnPayment(@RequestParam String vnp_ResponseCode,
                                                  @RequestParam String vnp_TxnRef,
                                                  HttpServletRequest req) throws Exception {
        String ipAddress = VNPayConfig.getIpAddress(req);
        VNPayDTO vnPayDTO = vnPayService.checkPayment(UUID.fromString(vnp_TxnRef), ipAddress, req);
        if (!vnPayDTO.getResponseCode().equals("00")) {
            throw new BadRequestException("Thanh toán không thành công cho đơn hàng #" + vnp_TxnRef + "!");
        }
        orderVNPayService.updateOrderStatusAfterPayment(UUID.fromString(vnp_TxnRef));

        return ResponseEntity.ok(vnPayDTO);
    }


}
