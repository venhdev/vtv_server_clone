package hcmute.kltn.vtv.model.data.shipping.response;

import hcmute.kltn.vtv.model.dto.shipping.TransportServiceProviderDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransportServiceProviderResponse extends ResponseAbstract {

    private TransportServiceProviderDTO transportServiceProviderDTO;
}
