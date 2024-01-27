package hcmute.kltn.vtv.repository.shipping;

import hcmute.kltn.vtv.model.entity.shipping.TransportProvider;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportProviderRepository extends JpaRepository<TransportProvider, Long> {
    // Các phương thức truy vấn cụ thể có thể được thêm ở đây

    Optional<List<TransportProvider>> findAllByStatus(Status status);

    Optional<TransportProvider> findByEmail(String email);

    Optional<TransportProvider> findByPhone(String phone);

    Optional<TransportProvider> findByCustomerUsername(String username);

}
