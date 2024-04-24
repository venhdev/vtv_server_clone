package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.request.MultipleOrderRequestWithCart;
import hcmute.kltn.vtv.model.data.user.response.MultipleOrderResponse;

import java.util.List;
import java.util.UUID;

public interface IMultipleOrderService {
    MultipleOrderResponse createMultipleOrderByCartIds(List<UUID> cartIds, String username);

    MultipleOrderResponse createMultipleOrderByRequest(MultipleOrderRequestWithCart request, String username);

    MultipleOrderResponse addNewMultipleOrderByRequest(MultipleOrderRequestWithCart request, String username);
}
