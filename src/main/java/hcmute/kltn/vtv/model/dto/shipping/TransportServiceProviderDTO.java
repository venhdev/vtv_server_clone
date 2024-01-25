package hcmute.kltn.vtv.model.dto.shipping;

import hcmute.kltn.vtv.model.dto.location.ProvinceDTO;
import hcmute.kltn.vtv.model.entity.shipping.TransportServiceProvider;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.extra.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportServiceProviderDTO {


    private Long transportServiceProviderId;

    private String fullName;

    private String shortName;

    private String usernameAdded;

    private Status status;

    private Long customerId;

    private List<ProvinceDTO> provinces;


    public static TransportServiceProviderDTO convertEntityToDTO(TransportServiceProvider transportServiceProvider){
        TransportServiceProviderDTO dto = new TransportServiceProviderDTO();
        dto.setTransportServiceProviderId(transportServiceProvider.getTransportServiceProviderId());
        dto.setFullName(transportServiceProvider.getFullName());
        dto.setShortName(transportServiceProvider.getShortName());
        dto.setUsernameAdded(transportServiceProvider.getUsernameAdded());
        dto.setStatus(transportServiceProvider.getStatus());
        dto.setCustomerId(transportServiceProvider.getCustomer().getCustomerId());
        dto.setProvinces(ProvinceDTO.convertEntitiesToDTOs(transportServiceProvider.getProvinces()));

        return dto;
    }

    public static List<TransportServiceProviderDTO> convertEntitiesToDTOs(List<TransportServiceProvider> transportServiceProviders){
        List<TransportServiceProviderDTO> dtos = new ArrayList<>();
        for (TransportServiceProvider transportServiceProvider : transportServiceProviders){
            dtos.add(convertEntityToDTO(transportServiceProvider));
        }
        dtos.sort(Comparator.comparing(TransportServiceProviderDTO::getFullName));

        return dtos;
    }


}
