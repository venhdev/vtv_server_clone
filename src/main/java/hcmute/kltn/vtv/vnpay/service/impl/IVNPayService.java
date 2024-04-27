package hcmute.kltn.vtv.vnpay.service.impl;

import hcmute.kltn.vtv.vnpay.model.VNPayDTO;
import hcmute.kltn.vtv.vnpay.model.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface IVNPayService {
    VNPayResponse createPaymentByVNPay(UUID orderId, String ipAddress, String username);

    VNPayDTO checkPayment(UUID orderId, String ipAddress, HttpServletRequest req) throws Exception;
}
