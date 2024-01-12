package hcmute.kltn.vtv.service.vendor;

import hcmute.kltn.vtv.model.entity.vtc.Order;
import hcmute.kltn.vtv.model.entity.vtc.OrderItem;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IOrderItemShopService {
    @Transactional
    List<OrderItem> updateStatusOrderItemByShop(Order order, Status status);
}
