


package hcmute.kltn.vtv.controller.shpping;


import hcmute.kltn.vtv.model.data.shipping.request.CashOrdersRequest;
import hcmute.kltn.vtv.model.data.shipping.response.CashOrdersByDatesResponse;
import hcmute.kltn.vtv.model.data.shipping.response.CashOrdersResponse;
import hcmute.kltn.vtv.service.shipping.ICashOrderService;
import hcmute.kltn.vtv.service.shipping.IDeliverService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping/cash-order")
@RequiredArgsConstructor
public class CashOrderController {


    private final ICashOrderService cashOrderService;
    private final IDeliverService deliverService;


    @PostMapping("/updates/transfers-money-warehouse")
    public ResponseEntity<CashOrdersResponse> shipperUpdateCashOrdersByShipper(@RequestBody CashOrdersRequest request,
                                                                               HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");
        request.validate();
        deliverService.checkExistByTypeWorkShipperByUsername(username);

        return ResponseEntity.ok(cashOrderService.updateCashOrdersWithWaveHouseHold(request, username, false, false));
    }



    @PostMapping("/updates/confirm-money-warehouse")
    public ResponseEntity<CashOrdersResponse> shipperUpdateCashOrdersByWaveHouse(@RequestBody CashOrdersRequest request,
                                                                                 HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");
        request.validate();
        deliverService.checkExistByTypeWorkWarehouseByUsername(username);

        return ResponseEntity.ok(cashOrderService.updateCashOrdersWithWaveHouseHold(request, username, false, true));
    }



    @GetMapping("/list-by-wave-house")
    public ResponseEntity<CashOrdersResponse> getCashOrdersByWaveHouseUsername(HttpServletRequest servletRequest) {
        String waveHouseUsername = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(cashOrderService.getListCashOrdersByWaveHouseUsernameAnhStatus(waveHouseUsername));
    }



    @GetMapping("/all-by-shipper")
    public ResponseEntity<CashOrdersResponse> getAllCashOrdersByShipperUsername(HttpServletRequest servletRequest) {
        String shipperUsername = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(cashOrderService.getListCashOrdersByShipperUsername(shipperUsername));
    }



    @GetMapping("/history-by-shipper")
    public ResponseEntity<CashOrdersByDatesResponse> historyCashOrdersByShipperUsername(HttpServletRequest servletRequest,
                                                                                        @RequestParam("shipperHold") boolean shipperHold,
                                                                                        @RequestParam("shipping") boolean shipping) {
        if (shipperHold && shipping) {
            throw new BadRequestException("Không thể lấy lịch sử đơn hàng với cả hai trạng thái: shipperHold và shipping cùng true!");
        }
        String shipperUsername = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(cashOrderService.historyCashOrdersByShipperUsernameAndShipperHold(shipperUsername, shipperHold, shipping));
    }



    @GetMapping("/history-by-warehouse")
    public ResponseEntity<CashOrdersByDatesResponse> historyCashOrdersByWarehouseUsername(HttpServletRequest servletRequest,
                                                                                          @RequestParam("warehouseHold") boolean warehouseHold,
                                                                                          @RequestParam("handlePayment") boolean handlePayment) {
        if (warehouseHold && handlePayment) {
            throw new BadRequestException("Không thể lấy lịch sử đơn hàng với cả hai trạng thái: warehouseHold và handlePayment cùng true!");
        }
        String warehouseUsername = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(cashOrderService.historyCashOrdersByWaveHouseUsernameAndWaveHouseHold(warehouseUsername, warehouseHold, handlePayment));
    }


}