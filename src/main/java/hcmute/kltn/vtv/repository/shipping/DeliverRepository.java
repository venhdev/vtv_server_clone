package hcmute.kltn.vtv.repository.shipping;

import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliverRepository extends JpaRepository<Deliver, Long> {

    boolean existsByCustomerCustomerId(Long customerId);

    Optional<List<Deliver>> findAllByStatus(Status status);

    Optional<List<Deliver>> findAllByStatusAndTypeWork(Status status, String typeWork);
}
