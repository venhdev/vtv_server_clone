package hcmute.kltn.vtv.model.data.admin.response;

import hcmute.kltn.vtv.model.dto.BrandDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AllBrandAdminResponse extends ResponseAbstract {

    private List<BrandDTO> brandDTOs;
}
