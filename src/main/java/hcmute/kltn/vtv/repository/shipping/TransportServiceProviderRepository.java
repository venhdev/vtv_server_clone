package hcmute.kltn.vtv.repository.shipping;

import hcmute.kltn.vtv.model.entity.shipping.TransportServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportServiceProviderRepository extends JpaRepository<TransportServiceProvider, Long> {
    // Các phương thức truy vấn cụ thể có thể được thêm ở đây
}
