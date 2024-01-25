package hcmute.kltn.vtv.model.data.shipping.response;

import hcmute.kltn.vtv.model.dto.shipping.TransportServiceProviderDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListTransportServiceProvidersResponse extends ResponseAbstract {

        private int count;

        private List<TransportServiceProviderDTO> transportServiceProviderDTOs;
}
