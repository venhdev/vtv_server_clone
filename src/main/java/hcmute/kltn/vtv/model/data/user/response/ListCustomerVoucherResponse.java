package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.VoucherDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListCustomerVoucherResponse extends ResponseAbstract {
    private String username;
    private int count;
    private List<VoucherDTO> voucherDTOs;
}