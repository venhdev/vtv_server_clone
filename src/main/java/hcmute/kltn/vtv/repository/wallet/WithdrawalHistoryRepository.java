package hcmute.kltn.vtv.repository.wallet;

import hcmute.kltn.vtv.model.entity.wallet.WithdrawalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WithdrawalHistoryRepository extends JpaRepository<WithdrawalHistory, UUID> {
}