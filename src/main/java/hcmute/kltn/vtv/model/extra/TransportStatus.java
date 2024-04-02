package hcmute.kltn.vtv.model.extra;

import lombok.Getter;

@Getter
public enum TransportStatus {

    PENDING,
    PROCESSING,
    WAITING,
    PICKUP_PENDING,
    PICKED_UP,
    IN_TRANSIT,
    WAREHOUSE,
    TRANSPORTING,
    DELIVERED,
    COMPLETED,
    RETURNED,
    CANCEL;
}
