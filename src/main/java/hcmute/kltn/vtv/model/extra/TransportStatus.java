package hcmute.kltn.vtv.model.extra;

import lombok.Getter;

@Getter
public enum TransportStatus {

    PENDING,
    PROCESSING,
    WAITING,
    UNPAID,
    PICKUP_PENDING,
    PICKED_UP,
    IN_TRANSIT,
    WAREHOUSE,
    SHIPPING,
    DELIVERED,
    COMPLETED,
    RETURNED,
    CANCEL;
}
