package hcmute.kltn.vtv.model.data.admin.request;


import hcmute.kltn.vtv.authentication.request.RegisterRequest;
import hcmute.kltn.vtv.model.dto.location.ProvinceDTO;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@RequiredArgsConstructor
public class TransportServiceProviderRegisterRequest {

    private String fullName;

    private String shortName;

    private String usernameAdded;

    private List<String> provincesCode;

    private RegisterRequest registerRequest;

    public void validate() {
        if (this.fullName == null || this.fullName.isEmpty()) {
            throw new BadRequestException("Tên đầy đủ không được để trống!");
        }

        if (this.shortName == null || this.shortName.isEmpty()) {
            throw new BadRequestException("Tên viết tắt không được để trống!");
        }

        if (this.usernameAdded == null || this.usernameAdded.isEmpty()) {
            throw new BadRequestException("Tên đăng nhập không được để trống!");
        }

        if (this.provincesCode == null || this.provincesCode.isEmpty()) {
            throw new BadRequestException("Tỉnh thành không được để trống!");
        }

        if(hasDuplicates(this.provincesCode)){
            throw new BadRequestException("Mã tỉnh thành không được trùng lặp.");
        }

        registerRequest.validate();

        trim();
    }

    public void trim() {
        this.fullName = this.fullName.trim();
        this.shortName = this.shortName.trim();
        this.usernameAdded = this.usernameAdded.trim();
    }


    public static boolean hasDuplicates(List<String> list) {
        Set<String> uniqueSet = new HashSet<>();

        for (String code : list) {
            if (!uniqueSet.add(code)) {
                return true;
            }
        }

        return false;
    }



}
