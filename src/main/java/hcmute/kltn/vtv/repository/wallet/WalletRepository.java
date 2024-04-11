package hcmute.kltn.vtv.repository.wallet;

import hcmute.kltn.vtv.model.entity.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByUsername(String username);


}