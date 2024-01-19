package hcmute.kltn.vtv.model.data.shipping.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hcmute.kltn.vtv.model.extra.EmailValidator;
import hcmute.kltn.vtv.model.extra.UUIDDeserializer;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.DuplicateEntryException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@ToString
@RequiredArgsConstructor
public class UpdateDeliverWorkRequest {

//    @JsonDeserialize(using = UUIDDeserializer.class)
    private Long deliverId;

    private String typeWork;

    private String usernameAdded;

    private String districtCodeWork;

    private List<String> wardsCodeWork;

    public void validate() {

        if (this.typeWork == null || this.typeWork.isEmpty()) {
            throw new BadRequestException("Loại công việc không được để trống.");
        }

        if (!this.typeWork.equals("shipper")
                && !this.typeWork.equals("shipper-transshipment")
                && !this.typeWork.equals("shipper-shop")
                && !this.typeWork.equals("shipper-warehouse")) {
            throw new BadRequestException(
                    "Loại công việc không hợp lệ. Loại công việc phải là shipper, shipper-transshipment, shipper-shop hoặc shipper-warehouse.");
        }


        if (this.usernameAdded == null || this.usernameAdded.isEmpty()) {
            throw new BadRequestException("Tên người đăng ký không được để trống.");
        }
        if (this.districtCodeWork == null || this.districtCodeWork.isEmpty()) {
            throw new BadRequestException("Mã quận/huyện làm việc không được để trống.");
        }
        if (this.wardsCodeWork == null || this.wardsCodeWork.isEmpty()) {
            throw new BadRequestException("Mã phường/xã làm việc không được để trống.");
        }
        if(hasDuplicates(this.wardsCodeWork)){
            throw new DuplicateEntryException("Mã phường/xã làm việc không được trùng lặp.");
        }

        trim();
    }


    public static boolean hasDuplicates(List<String> list) {
        Set<String> uniqueSet = new HashSet<>();

        for (String code : list) {
            // Nếu thêm vào được tức là chưa tồn tại trong Set, ngược lại là đã tồn tại (trùng lặp)
            if (!uniqueSet.add(code)) {
                return true; // Trùng lặp được tìm thấy
            }
        }

        return false; // Không có trùng lặp
    }

    public void trim() {
        this.typeWork = this.typeWork.trim();
        this.usernameAdded = this.usernameAdded.trim();
        this.districtCodeWork = this.districtCodeWork.trim();
    }

}
