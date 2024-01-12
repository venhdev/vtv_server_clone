package hcmute.kltn.vtv.model.data.guest;

import hcmute.kltn.vtv.model.dto.VoucherDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListVoucherResponse extends ResponseAbstract {
    private int count;
    private List<VoucherDTO> voucherDTOs;
}
