package hcmute.kltn.vtv.model.dto;

import hcmute.kltn.vtv.model.dto.location_dto.WardDTO;
import hcmute.kltn.vtv.model.entity.vtc.Deliver;
import hcmute.kltn.vtv.model.extra.Status;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliverDTO {

    private UUID deliverId;

    private String phone;

    private String email;

    private String province;

    private String district;

    private String ward;

    private String fullAddress;

    private String typeWork;

    private String usernameAdded;

    private Status status;

    private String districtWork;

    private List<String> wardsWork;

    public static DeliverDTO convertEntityToDTO(Deliver deliver) {
        DeliverDTO deliverDTO = new DeliverDTO();
        deliverDTO.setDeliverId(deliver.getDeliverId());
        deliverDTO.setPhone(deliver.getPhone());
        deliverDTO.setEmail(deliver.getEmail());
        deliverDTO.setProvince(deliver.getProvince());
        deliverDTO.setDistrict(deliver.getDistrict());
        deliverDTO.setWard(deliver.getWard());
        deliverDTO.setFullAddress(deliver.getFullAddress());
        deliverDTO.setTypeWork(deliver.getTypeWork());
        deliverDTO.setUsernameAdded(deliver.getUsernameAdded());
        deliverDTO.setStatus(deliver.getStatus());
        deliverDTO.setDistrictWork(deliver.getDistrictWork().getName());
        deliverDTO.setWardsWork(WardDTO.convertEntitiesToNames(deliver.getWardsWork()));
        return deliverDTO;
    }

}
