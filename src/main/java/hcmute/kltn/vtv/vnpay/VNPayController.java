package hcmute.kltn.vtv.vnpay;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/vnpay")
@RequiredArgsConstructor
public class VNPayController {

    private final IVNPayService vnpayService;

    @GetMapping("/create-payment")
    public ResponseEntity<VNPayResponse> createPayment(HttpServletRequest req) {

//        String orderType = "other";
//
//        String vnp_ReturnUrl = "http://localhost:8585/api/payment/vnpay/return";
//
//        Long amount = 100000000L;
//
//        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
//        String vnp_IpAddr = VNPayConfig.getIpAddress(req);
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
//        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
//        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", "NCB");
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//        vnp_Params.put("vnp_OrderType", orderType);
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List fieldNames = new ArrayList(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        Iterator itr = fieldNames.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
//                //Build hash data
//                hashData.append(fieldName);
//                hashData.append('=');
//                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
//                //Build query
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
//                query.append('=');
//                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
//                if (itr.hasNext()) {
//                    query.append('&');
//                    hashData.append('&');
//                }
//            }
//        }
//        String queryUrl = query.toString();
//        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
//
//        VNPayResponse VNPayResponse = new VNPayResponse();
//        VNPayResponse.setUrl(paymentUrl);
//        VNPayResponse.setStatus("success");
//        VNPayResponse.setMessage("Tạo đơn hàng thành công.");


        return ResponseEntity.ok(vnpayService.createPayment(req));
    }

    @GetMapping("/return")
    public ResponseEntity<?> returnPayment(@RequestParam String vnp_ResponseCode,
                                           @RequestParam String vnp_TxnRef
    ) {
        System.out.println("vnp_ResponseCode: " + vnp_ResponseCode);
        System.out.println("vnp_TxnRef: " + vnp_TxnRef);

        return ResponseEntity.status(HttpStatus.OK).body("Thanh toán thành công." + vnp_ResponseCode + " " + vnp_TxnRef);
    }


}
