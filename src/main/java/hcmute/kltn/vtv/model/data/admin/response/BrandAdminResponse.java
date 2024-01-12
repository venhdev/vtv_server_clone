package hcmute.kltn.vtv.model.data.admin.response;

import hcmute.kltn.vtv.model.dto.BrandDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BrandAdminResponse extends ResponseAbstract {

    private BrandDTO brandDTO;
}
