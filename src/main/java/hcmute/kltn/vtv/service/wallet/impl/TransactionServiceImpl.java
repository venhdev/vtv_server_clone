package hcmute.kltn.vtv.service.wallet.impl;

import hcmute.kltn.vtv.repository.wallet.TransactionRepository;
import hcmute.kltn.vtv.service.wallet.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
    private final TransactionRepository transactionRepository;




}
