package hcmute.kltn.vtv.model.data.user.response;

import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultipleOrderResponse extends ResponseAbstract {

    private int count;
    private List<OrderResponse> orderResponses;

    public static MultipleOrderResponse multipleOrderResponse(List<OrderResponse> orderResponses, String message, String status) {
        MultipleOrderResponse response = new MultipleOrderResponse();
        response.setOrderResponses(orderResponses);
        response.setCount(orderResponses.size());
        response.setMessage(message);
        response.setStatus(status);
        response.setCode(200);

        return response;
    }

}
