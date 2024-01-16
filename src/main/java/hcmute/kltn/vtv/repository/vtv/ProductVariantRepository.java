package hcmute.kltn.vtv.repository.vtv;

import hcmute.kltn.vtv.model.entity.vtv.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    ProductVariant findByProductVariantId(Long productVariantId);

    boolean existsBySkuAndProductProductId(String sku, Long productId);

    List<ProductVariant> findAllByProductProductId(Long productId);

    ProductVariant getProductByProductVariantId(Long productVariantId);

}
