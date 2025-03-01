package hcmute.kltn.vtv.repository.user;

import hcmute.kltn.vtv.model.entity.user.Cart;
import hcmute.kltn.vtv.model.extra.CartStatus;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    boolean existsByCartIdAndCustomerUsername(UUID cartId, String username);

    boolean existsByCartIdAndCustomerUsernameAndStatus(UUID cartId, String username, CartStatus status);

    boolean existsByCartIdInAndCustomerUsernameAndStatus(List<UUID> cartIds, String username, CartStatus status);

    boolean existsByProductVariantProductVariantIdAndCustomerUsernameAndStatus(Long productVariantId, String username,
                                                                               CartStatus status);

    Optional<Cart> findByProductVariantProductVariantIdAndCustomerUsernameAndStatus(Long productVariantId,
            String username, CartStatus status);

    Optional<Cart> findByCartIdAndCustomerUsername(UUID cartId, String username);



    Optional<List<Cart>> findAllByCustomerUsernameAndStatus(String username, CartStatus status);

    Optional<List<Cart>> findAllByCustomerUsernameAndStatusAndCartIdIn(String username, CartStatus status,
            List<UUID> cartIds);

    Optional<List<Cart>> findAllByCustomerUsernameAndProductVariantProductShopShopIdAndStatus(String username,
            Long shopId, CartStatus status);


}
