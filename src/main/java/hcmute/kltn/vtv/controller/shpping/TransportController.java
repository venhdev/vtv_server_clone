package hcmute.kltn.vtv.controller.shpping;


import hcmute.kltn.vtv.model.data.shipping.response.TransportResponse;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import hcmute.kltn.vtv.service.shipping.ITransportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/shipping/transport")
@RequiredArgsConstructor
public class TransportController{

    private final ITransportService transportService;


    @PatchMapping("/update-status/{transportId}")
    public ResponseEntity<TransportResponse> updateStatusTransportByDeliver(@PathVariable("transportId") UUID transportId,
                                                                            @RequestParam("status") TransportStatus status,
                                                                            @RequestParam("handled") boolean handled,
                                                                            @RequestParam("wardCode") String wardCode,
                                                                            HttpServletRequest servletRequest) {
        if (!status.equals(TransportStatus.DELIVERED) && !status.equals(TransportStatus.PICKUP_PENDING)
                && !status.equals(TransportStatus.IN_TRANSIT) && !status.equals(TransportStatus.WAREHOUSE) &&
                !status.equals(TransportStatus.SHIPPING) && !status.equals(TransportStatus.RETURNED)) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ! Nhân viên vận chuyển chỉ có thể cập nhật trạng thái: " +
                    "DELIVERED, PICKUP_PENDING, IN_TRANSIT, WAREHOUSE, SHIPPING, RETURNED");
        }
        if (wardCode == null || wardCode.isEmpty()) {
            throw new IllegalArgumentException("Mã phường không được để trống!");
        }
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(transportService.updateStatusByDeliver(transportId, username, handled, status, wardCode));
    }
}
