package hcmute.kltn.vtv.repository.vtv;

import hcmute.kltn.vtv.model.entity.vtv.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByName(String name);

    List<Brand> findAllByAdminOnly(boolean adminOnly);

    Optional<Brand> findByBrandIdAndCustomerUsername(Long brandId, String username);

}
