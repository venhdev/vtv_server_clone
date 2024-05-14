package hcmute.kltn.vtv.repository.shipping;

import hcmute.kltn.vtv.model.entity.shipping.CashOrder;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CashOrderRepository extends JpaRepository<CashOrder, UUID> {

    boolean existsByTransportId(UUID transportId);

    boolean existsAllByCashOrderIdInAndShipperUsernameAndStatus(List<UUID> transportIds, String shipperUsername, Status status);

    boolean existsAllByCashOrderIdInAndWaveHouseUsernameAndStatus(List<UUID> transportIds, String waveHouseUsername, Status status);


    boolean existsByTransportIdAndShipperHoldAndWaveHouseHoldAndHandlePayment(UUID transportId, boolean shipperHold, boolean waveHouseHold, boolean handlePayment);

    boolean existsByOrderIdAndShipperHoldAndWaveHouseHoldAndHandlePaymentAndStatus(UUID orderId, boolean shipperHold, boolean waveHouseHold, boolean handlePayment, Status status);

    boolean existsAllByCashOrderIdInAndShipperUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(List<UUID> transportIds, String shipperUsername, boolean waveHouseHold, boolean shipperHold, boolean handlePayment, Status status);


    boolean existsAllByCashOrderIdInAndWaveHouseUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(List<UUID> transportIds, String waveHouseUsername, boolean waveHouseHold, boolean shipperHold, boolean handlePayment, Status status);


    Optional<CashOrder> findByTransportId(UUID transportId);

    Optional<CashOrder> findByOrderId(UUID orderId);

    Optional<List<CashOrder>> findAllByCashOrderIdInAndStatus(List<UUID> transportIds, Status status);

    Optional<List<CashOrder>> findAllByShipperUsernameAndStatus(String shipperUsername, Status status);

    Optional<List<CashOrder>> findAllByWaveHouseUsernameAndStatus(String waveHouseUsername,  Status status);

    Optional<List<CashOrder>> findAllByShipperUsername(String shipperUsername);

    Optional<List<CashOrder>> findAllByShipperUsernameAndShipperHoldAndWaveHouseUsernameNull(String shipperUsername, boolean shipperHold);

    Optional<List<CashOrder>> findAllByShipperUsernameAndShipperHoldAndWaveHouseUsernameNotNull(String shipperUsername, boolean shipperHold);

    Optional<List<CashOrder>> findAllByWaveHouseUsernameAndWaveHouseHoldAndShipperHoldAndHandlePaymentAndStatus(String waveHouseUsername, boolean waveHouseHold, boolean shipperHold, boolean handlePayment, Status status);


}