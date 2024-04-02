package hcmute.kltn.vtv.repository.shipping;

import hcmute.kltn.vtv.model.entity.shipping.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {

    Optional<Transport> findByTransportId(UUID transportId);

    Optional<Transport> findByOrderId(UUID orderId);
}
