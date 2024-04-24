package hcmute.kltn.vtv.service.user;

import hcmute.kltn.vtv.model.data.user.response.MultipleOrderResponse;

import java.util.List;
import java.util.UUID;

public interface IMultipleOrderService {
    MultipleOrderResponse createMultipleOrderByCartIds(List<UUID> cartIds, String username);
}
