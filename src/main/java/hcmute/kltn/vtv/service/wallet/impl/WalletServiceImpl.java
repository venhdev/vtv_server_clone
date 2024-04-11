package hcmute.kltn.vtv.service.wallet.impl;


import hcmute.kltn.vtv.model.entity.wallet.Wallet;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.wallet.WalletRepository;
import hcmute.kltn.vtv.service.wallet.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private final WalletRepository walletRepository;

    @Async
    @Override
    @Transactional
    public void addNewWalletAfterRegister(String username) {
        if (!walletRepository.existsByUsername(username)) {
            try {
                walletRepository.save(createWalletByUsername(username));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private Wallet createWalletByUsername(String username) {
        Wallet wallet = new Wallet();
        wallet.setUsername(username);
        wallet.setBalance(0L);
        wallet.setStatus(Status.ACTIVE);
        wallet.setCreateAt(LocalDateTime.now());
        wallet.setUpdateAt(LocalDateTime.now());

        return wallet;
    }



}
