package hcmute.kltn.vtv.model.extra;

import lombok.Getter;

@Getter
public enum TransportStatus {

    WAITING,
    RECEIVED,
    WAREHOUSE,
    IN_TRANSIT,
    TRANSPORTING,
    DELIVERED,
    RETURNED,
    CANCEL;



}
