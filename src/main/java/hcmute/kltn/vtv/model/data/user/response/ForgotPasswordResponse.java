package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordResponse extends ResponseAbstract {

    private String username;

    private String email;

    private long timeValid;
}
