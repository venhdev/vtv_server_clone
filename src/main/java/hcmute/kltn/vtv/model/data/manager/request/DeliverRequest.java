package hcmute.kltn.vtv.model.data.manager.request;

import hcmute.kltn.vtv.model.entity.location.District;
import hcmute.kltn.vtv.model.entity.location.Ward;
import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.extra.EmailValidator;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@Data
@ToString
@RequiredArgsConstructor
public class DeliverRequest {

    private UUID deliverId;

    private String phone;

    private String email;

    private String province;

    private String district;

    private String ward;

    private String wardCode;

    private String fullAddress;

    private String typeWork;

    private String usernameAdded;

    private String districtCodeWork;

    private List<String> wardsCodeWork;

    private Long customerId;


    public void validate() {
        if (this.phone == null || this.phone.isEmpty()) {
            throw new BadRequestException("Số điện thoại không được để trống.");
        }

        if (!Pattern.matches("[0-9]+", phone)) {
            throw new BadRequestException("Số điện thoại chỉ được chứa ký tự số.");
        }

        if (phone.length() < 9 || phone.length() > 11) {
            throw new BadRequestException("Số điện thoại không hợp lệ.");
        }

        if (this.email == null || this.email.isEmpty()) {
            throw new BadRequestException("Email không được để trống.");
        } else if (!EmailValidator.isValidEmail(email)) {
            throw new BadRequestException("Email không hợp lệ.");
        }
        if (this.province == null || this.province.isEmpty()) {
            throw new BadRequestException("Tỉnh/Thành phố không được để trống.");
        }
        if (this.district == null || this.district.isEmpty()) {
            throw new BadRequestException("Quận/Huyện không được để trống.");
        }
        if (this.ward == null || this.ward.isEmpty()) {
            throw new BadRequestException("Phường/Xã không được để trống.");
        }
        if (this.wardCode == null || this.wardCode.isEmpty()) {
            throw new BadRequestException("Mã phường/xã không được để trống.");
        }
        if (this.fullAddress == null || this.fullAddress.isEmpty()) {
            throw new BadRequestException("Địa chỉ không được để trống.");
        }
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
            throw new BadRequestException("Mã phường/xã làm việc không được trùng lặp.");
        }

        if (this.customerId == null || this.customerId <= 0) {
            throw new BadRequestException("Mã khách hàng không được để trống.");
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
        this.phone = this.phone.trim();
        this.email = this.email.trim();
        this.province = this.province.trim();
        this.district = this.district.trim();
        this.ward = this.ward.trim();
        this.wardCode = this.wardCode.trim();
        this.fullAddress = this.fullAddress.trim();
        this.typeWork = this.typeWork.trim();
        this.usernameAdded = this.usernameAdded.trim();
        this.districtCodeWork = this.districtCodeWork.trim();
    }


    public static Deliver convertRequestToEntity(DeliverRequest deliverRequest) {
        Deliver deliver = new Deliver();
//        deliver.setDeliverId(deliverRequest.getDeliverId());
        deliver.setPhone(deliverRequest.getPhone());
        deliver.setEmail(deliverRequest.getEmail());
        deliver.setProvince(deliverRequest.getProvince());
        deliver.setDistrict(deliverRequest.getDistrict());
        deliver.setWard(deliverRequest.getWard());
        deliver.setWardCode(deliverRequest.getWardCode());
        deliver.setFullAddress(deliverRequest.getFullAddress());
        deliver.setTypeWork(deliverRequest.getTypeWork());
        deliver.setUsernameAdded(deliverRequest.getUsernameAdded());

        return deliver;
    }


}
