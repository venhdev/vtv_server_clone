package hcmute.kltn.vtv.controller.vtv;

import hcmute.kltn.vtv.service.vtv.ILoyaltyPointSchedulerService;
import hcmute.kltn.vtv.service.vtv.IOrderSchedulerService;
import hcmute.kltn.vtv.service.vtv.ITokenSchedulerService;
import hcmute.kltn.vtv.service.vtv.IVoucherSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DailyUpdateScheduler {

    private final IVoucherSchedulerService voucherSchedulerService;
    private final ITokenSchedulerService tokenSchedulerService;
    private final IOrderSchedulerService orderSchedulerService;
    private final ILoyaltyPointSchedulerService loyaltyPointSchedulerService;
//    private final IWalletService walletService;

    //    private final ILoyaltyPointService loyaltyPointService;
//    private final CustomerRepository customerRepository;


    @PostConstruct
    public void onServerStart() {
        System.out.println("Server kích hoạt lúc " + LocalDate.now() + " - Thực hiện cập nhật hàng ngày...");
        voucherSchedulerService.checkExpirationVoucher();
        tokenSchedulerService.checkExpirationToken();
        tokenSchedulerService.deleteTokenExpiredAndRevoked();
        orderSchedulerService.autoCompleteOrderAfterFiveDayDelivered();
        loyaltyPointSchedulerService.resetLoyaltyPointBeforeDate(LocalDate.now().atStartOfDay().minusDays(7));
//        List<Customer> customers = customerRepository.findAll();
//        for (Customer customer : customers) {
//            loyaltyPointService.addNewLoyaltyPointAfterRegister(customer.getUsername());
//        }

//        List<Customer> customers = customerRepository.findAll();
//        for (Customer customer : customers) {
//            walletService.addNewWalletAfterRegister(customer.getUsername());
//        }


    }


    // This method will be executed every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void performDailyUpdate() {
        System.out.println("Thực hiện cập nhật hàng ngày vào lúc " + LocalDate.now());
        voucherSchedulerService.checkExpirationVoucher();
        tokenSchedulerService.checkExpirationToken();
        tokenSchedulerService.deleteTokenExpiredAndRevoked();
        orderSchedulerService.autoCompleteOrderAfterFiveDayDelivered();
        loyaltyPointSchedulerService.resetLoyaltyPointBeforeDate(LocalDate.now().atStartOfDay().minusDays(7));

    }
}

