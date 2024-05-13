package hcmute.kltn.vtv.repository.shipping;

import hcmute.kltn.vtv.model.entity.shipping.CashOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CashOrderRepository extends JpaRepository<CashOrder, UUID> {
}