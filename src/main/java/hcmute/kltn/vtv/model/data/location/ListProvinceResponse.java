package hcmute.kltn.vtv.model.data.location;

import hcmute.kltn.vtv.model.dto.location.ProvinceDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListProvinceResponse extends ResponseAbstract {

        private int count;
        private List<ProvinceDTO> provinceDTOs;
}
