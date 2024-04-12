package hcmute.kltn.vtv.model.dto.wallet;

import hcmute.kltn.vtv.model.entity.wallet.Transaction;
import hcmute.kltn.vtv.model.extra.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private UUID transactionId;
    private Long walletId;
    private UUID orderId;
    private Long money;
    private String type;
    private Status status;
    private LocalDateTime transactionAt;


    public static TransactionDTO convertEntityToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setWalletId(transaction.getWallet().getWalletId());
        transactionDTO.setOrderId(transaction.getOrderId());
        transactionDTO.setMoney(transaction.getMoney());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setStatus(transaction.getStatus());
        transactionDTO.setTransactionAt(transaction.getTransactionAt());

        return transactionDTO;
    }




    public static List<TransactionDTO> convertEntitiesToDTOs(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionDTO::convertEntityToDTO)
                .sorted((o1, o2) -> o2.getTransactionAt().compareTo(o1.getTransactionAt()))
                .collect(Collectors.toList());
    }


}