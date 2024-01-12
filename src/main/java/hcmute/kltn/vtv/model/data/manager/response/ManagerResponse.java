package hcmute.kltn.vtv.model.data.manager.response;

import hcmute.kltn.vtv.model.dto.ManagerDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponse extends ResponseAbstract {

    private ManagerDTO managerDTO;
}
