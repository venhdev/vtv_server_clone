package hcmute.kltn.vtv.model.data.location;

import hcmute.kltn.vtv.model.dto.location.WardDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListWardResponse extends ResponseAbstract {

    private int count;
    private String districtCode;
    List<WardDTO> wardDTOs;
}