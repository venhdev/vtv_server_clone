package hcmute.kltn.vtv.model.data.manager.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class ManagerCustomerRequest {

    private Long customerId;
    private String managerUsername;
    private String note;
    private boolean isLock;

    public void validate(){
        if(this.customerId == null || this.customerId <= 0){
            throw new IllegalArgumentException("Mã khách hàng không hợp lệ");
        }
    }

}
