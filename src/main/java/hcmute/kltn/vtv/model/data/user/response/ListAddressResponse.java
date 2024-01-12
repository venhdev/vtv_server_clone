package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.AddressDTO;
import hcmute.kltn.vtv.model.dto.CustomerDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListAddressResponse extends ResponseAbstract {

    private List<AddressDTO> addressDTOs;
    private CustomerDTO customerDTO;
}
