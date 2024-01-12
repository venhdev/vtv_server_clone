package hcmute.kltn.vtv.repository;

import hcmute.kltn.vtv.model.entity.vtc.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
}
