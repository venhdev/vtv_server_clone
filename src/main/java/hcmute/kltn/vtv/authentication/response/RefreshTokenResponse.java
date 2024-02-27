package hcmute.kltn.vtv.authentication.response;

import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse extends ResponseAbstract {

        private String accessToken;
}
