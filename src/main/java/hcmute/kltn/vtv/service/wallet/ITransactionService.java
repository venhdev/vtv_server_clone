package hcmute.kltn.vtv.service.wallet;

import hcmute.kltn.vtv.model.entity.wallet.Transaction;
import hcmute.kltn.vtv.model.entity.wallet.Wallet;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ITransactionService {
    @Transactional
    Transaction addNewTransaction(Wallet wallet, UUID orderId, Long money, String type);
}
