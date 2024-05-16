package hcmute.kltn.vtv.controller.shpping;


import hcmute.kltn.vtv.model.data.shipping.request.ShopAndTransportResponse;
import hcmute.kltn.vtv.model.data.shipping.response.TransportResponse;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import hcmute.kltn.vtv.service.shipping.ITransportService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/shipping/transport")
@RequiredArgsConstructor
public class TransportController {

    private final ITransportService transportService;


    @PatchMapping("/update-status/{transportId}")
    public ResponseEntity<TransportResponse> updateStatusTransportByDeliver(@PathVariable("transportId") UUID transportId,
                                                                            @RequestParam("status") TransportStatus status,
                                                                            @RequestParam("handled") boolean handled,
                                                                            @RequestParam("wardCode") String wardCode,
                                                                            HttpServletRequest servletRequest) {
        if (!status.equals(TransportStatus.DELIVERED)
                && !status.equals(TransportStatus.IN_TRANSIT) && !status.equals(TransportStatus.WAREHOUSE) &&
                !status.equals(TransportStatus.SHIPPING) && !status.equals(TransportStatus.RETURNED) &&
                !status.equals(TransportStatus.CANCEL) && !status.equals(TransportStatus.PICKED_UP)) {
            throw new BadRequestException("Trạng thái không hợp lệ! Nhân viên vận chuyển chỉ có thể cập nhật trạng thái: " +
                    "DELIVERED, IN_TRANSIT, WAREHOUSE, SHIPPING, RETURNED, CANCEL, PICKED_UP");
        }
        if (wardCode == null || wardCode.isEmpty()) {
            throw new BadRequestException("Mã phường không được để trống!");
        }
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(transportService.updateTransportStatusByDeliver(transportId, username, handled, status, wardCode));
    }


    @PatchMapping("/return/update-status/{transportId}")
    public ResponseEntity<TransportResponse> updateTransportStatusWithReturnOrderByDeliver(@PathVariable("transportId") UUID transportId,
                                                                                           @RequestParam("status") TransportStatus status,
                                                                                           @RequestParam("handled") boolean handled,
                                                                                           @RequestParam("wardCode") String wardCode,
                                                                                           HttpServletRequest servletRequest) {
        if (!status.equals(TransportStatus.DELIVERED)
                && !status.equals(TransportStatus.IN_TRANSIT) && !status.equals(TransportStatus.WAREHOUSE) &&
                !status.equals(TransportStatus.SHIPPING) && !status.equals(TransportStatus.RETURNED) &&
                !status.equals(TransportStatus.CANCEL) && !status.equals(TransportStatus.PICKED_UP)) {
            throw new BadRequestException("Trạng thái không hợp lệ! Nhân viên vận chuyển chỉ có thể cập nhật trạng thái: " +
                    "DELIVERED, IN_TRANSIT, WAREHOUSE, SHIPPING, RETURNED, CANCEL, PICKED_UP");
        }
        if (wardCode == null || wardCode.isEmpty()) {
            throw new BadRequestException("Mã phường không được để trống!");
        }
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(transportService.updateTransportStatusWithReturnOrderByDeliver(transportId, username, handled, status, wardCode));
    }


    @PatchMapping("/cancel-return/{transportId}")
    public ResponseEntity<TransportResponse> updateTransportStatusWithCancelReturnOrderByDeliver(@PathVariable("transportId") UUID transportId,
                                                                                                 @RequestBody String reason,
                                                                                                 HttpServletRequest servletRequest) {
        if (reason == null || reason.isEmpty()) {
            throw new BadRequestException("Lý do trả hàng thất bại không được để trống!");
        }
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(transportService.updateTransportStatusWithCancelReturnOrderByDeliver(transportId, username, reason));
    }


    @PatchMapping("/success-return/{transportId}")
    public ResponseEntity<TransportResponse> updateTransportStatusWithSuccessReturnOrderByDeliver(@PathVariable("transportId") UUID transportId,
                                                                                                  @RequestBody String reason,
                                                                                                  HttpServletRequest servletRequest) {
        if (reason == null || reason.isEmpty()) {
            throw new BadRequestException("Lý do trả hàng thành công không được để trống!");
        }
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(transportService.updateTransportStatusWithSuccessReturnOrderByDeliver(transportId, username, reason));
    }



    @GetMapping("/get-by-ward-work")
    public ResponseEntity<ShopAndTransportResponse> getTransportsByWardWorksDeliver(HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");


        return ResponseEntity.ok(transportService.getTransportsByWardWorksDeliver(username));
    }


    @GetMapping("/get/{transportId}")
    public ResponseEntity<TransportResponse> getTransportResponseByTransportId(@PathVariable("transportId") UUID transportId) {

        return ResponseEntity.ok(transportService.getTransportResponseByTransportId(transportId));
    }


    @GetMapping("/get/ward/{wardCode}")
    public ResponseEntity<ShopAndTransportResponse> getTransportsByWard(@PathVariable("wardCode") String wardCode,
                                                                        HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(transportService.getTransportsByWard(wardCode, username));
    }
}
