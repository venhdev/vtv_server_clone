package hcmute.kltn.vtv.model.data.vendor.response;

import hcmute.kltn.vtv.model.dto.vtv.VoucherDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListVoucherShopResponse extends ResponseAbstract {
    private Long shopId;
    private String shopName;
    private int count;
    private List<VoucherDTO> voucherDTOs;
}
