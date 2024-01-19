package hcmute.kltn.vtv.model.data.shipping.response;

import hcmute.kltn.vtv.model.dto.shipping.DeliverDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliverResponse extends ResponseAbstract {

    private DeliverDTO deliverDTO;
}
