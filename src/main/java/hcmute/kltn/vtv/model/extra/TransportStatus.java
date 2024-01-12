package hcmute.kltn.vtv.model.extra;

import lombok.Getter;

@Getter
public enum TransportStatus {

    WAITING("Đang chờ"),
    RECEIVED("Đã nhận hàng"),
    IN_TRANSIT("Đang trung chuyển"),
    TRANSPORTING("Đang vận chuyển"),
    DELIVERED("Đã giao hàng"),
    RETURNED("Đã trả hàng"),
    CANCEL("Đã hủy");

    private final TransportStatus value;

    TransportStatus(String value) {
        this.value = TransportStatus.valueOf(value);
    }



}
