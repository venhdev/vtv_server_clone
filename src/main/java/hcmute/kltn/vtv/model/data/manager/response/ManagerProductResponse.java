package hcmute.kltn.vtv.model.data.manager.response;

import hcmute.kltn.vtv.model.dto.manager.ManagerProductDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerProductResponse extends ResponseAbstract {

    private ManagerProductDTO managerProductDTO;

}
