package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.VoucherDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVoucherResponse extends ResponseAbstract {
    private String username;
    private VoucherDTO voucherDTO;
}
