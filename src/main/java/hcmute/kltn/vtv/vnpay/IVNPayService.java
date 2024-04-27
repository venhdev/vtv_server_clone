package hcmute.kltn.vtv.vnpay;

import jakarta.servlet.http.HttpServletRequest;

public interface IVNPayService {
    VNPayResponse createPayment(HttpServletRequest req);
}
