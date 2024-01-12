package hcmute.kltn.vtv.model.data.location;

import hcmute.kltn.vtv.model.dto.location_dto.DistrictDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DistrictResponse extends ResponseAbstract {

    private DistrictDTO districtDTO;
}
