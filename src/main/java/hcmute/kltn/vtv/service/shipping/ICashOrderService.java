package hcmute.kltn.vtv.service.shipping;

import hcmute.kltn.vtv.model.data.shipping.request.CashOrdersRequest;
import hcmute.kltn.vtv.model.data.shipping.response.CashOrdersByDatesResponse;
import hcmute.kltn.vtv.model.data.shipping.response.CashOrdersResponse;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ICashOrderService {
    @Async
    @Transactional
    void addNewCashOrder(UUID transportId, String shipperUsername, boolean shipperHold, Status status);



    @Transactional
    void updateCashOrderWithWaveHouseHold(UUID transportId, boolean shipperHold, String waveHouseUsername, boolean waveHouseHold);




    @Async
    @Transactional
    void updateCashOrderByOrderIdWithHandlePayment(UUID orderId);


    @Transactional
    CashOrdersResponse updateCashOrdersWithWaveHouseHold(CashOrdersRequest cashOrdersRequest, String username, boolean shipperHold,
                                                         boolean waveHouseHold);

    CashOrdersResponse getListCashOrdersByShipperUsername(String shipperUsername);

    CashOrdersResponse getListCashOrdersByWaveHouseUsernameAnhStatus(String waveHouseUsername);

    CashOrdersByDatesResponse historyCashOrdersByShipperUsernameAndShipperHold(String shipperUsername, boolean shipperHold, boolean shipping);

    CashOrdersByDatesResponse historyCashOrdersByWaveHouseUsernameAndWaveHouseHold(String waveHouseUsername,
                                                                                   boolean waveHouseHold,
                                                                                   boolean handlePayment);

    boolean checkCashOrderByOrderIdWithHandlePaymentAndStatus(UUID orderId, boolean shipperHold, boolean waveHouseHold, boolean handlePayment, Status status);

    boolean checkCashOrderByOrderIdWithHandlePayment(UUID orderId, boolean shipperHold, boolean waveHouseHold, boolean handlePayment);
}
