package hcmute.kltn.vtv.model.data.guest;

import hcmute.kltn.vtv.model.dto.VoucherDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse extends ResponseAbstract {
    private VoucherDTO voucherDTO;
}
