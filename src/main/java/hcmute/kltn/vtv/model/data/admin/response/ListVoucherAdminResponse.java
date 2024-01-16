package hcmute.kltn.vtv.model.data.admin.response;

import hcmute.kltn.vtv.model.dto.vtv.VoucherDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListVoucherAdminResponse extends ResponseAbstract {

    private String username;
    private int count;
    private List<VoucherDTO> voucherDTOs;
}
