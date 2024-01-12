package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.dto.CustomerDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCustomerResponse extends ResponseAbstract {

    private CustomerDTO customerDTO;

}
