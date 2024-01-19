package hcmute.kltn.vtv.model.dto.shipping;

import hcmute.kltn.vtv.model.dto.location.WardDTO;
import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.extra.Status;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliverDTO {

    private Long deliverId;

    private String phone;

    private String email;

    private String province;

    private String district;

    private String ward;

    private String wardCode;

    private String fullAddress;

    private String typeWork;

    private String usernameAdded;

    private Status status;

    private String districtWork;

    private List<String> wardsWork;

    private Long customerId;

    public static DeliverDTO convertEntityToDTO(Deliver deliver) {
        DeliverDTO deliverDTO = new DeliverDTO();
        deliverDTO.setDeliverId(deliver.getDeliverId());
//        deliverDTO.setPhone(deliver.getPhone());
//        deliverDTO.setEmail(deliver.getEmail());
        deliverDTO.setProvince(deliver.getProvince());
        deliverDTO.setDistrict(deliver.getDistrict());
        deliverDTO.setWard(deliver.getWard());
        deliverDTO.setWardCode(deliver.getWardCode());
        deliverDTO.setFullAddress(deliver.getFullAddress());
        deliverDTO.setTypeWork(deliver.getTypeWork());
        deliverDTO.setUsernameAdded(deliver.getUsernameAdded());
        deliverDTO.setStatus(deliver.getStatus());
        deliverDTO.setDistrictWork(deliver.getDistrictWork().getName());
        deliverDTO.setWardsWork(WardDTO.convertEntitiesToNames(deliver.getWardsWork()));
        deliverDTO.setCustomerId(deliver.getCustomer().getCustomerId());
        return deliverDTO;
    }


    public static List<DeliverDTO> convertEntitiesToDTOs(List<Deliver> delivers) {
        List<DeliverDTO> deliverDTOs = new ArrayList<>();
        for (Deliver deliver : delivers) {
            deliverDTOs.add(convertEntityToDTO(deliver));
        }

        if (!deliverDTOs.isEmpty()){
            deliverDTOs.sort((o1, o2) -> o1.getUsernameAdded().compareTo(o2.getUsernameAdded()));
        }
        return deliverDTOs;
    }

}
