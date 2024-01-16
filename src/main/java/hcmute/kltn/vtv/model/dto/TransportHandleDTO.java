package hcmute.kltn.vtv.model.dto;

import hcmute.kltn.vtv.model.entity.vtc.TransportHandle;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import hcmute.kltn.vtv.service.location.IWardService;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransportHandleDTO {

    private UUID transportHandleId;

    private String username;

    // vi tri cua shipper
    private String wardCode;

    private String wardName;

    private boolean isActive;

    // thể hiện đã xử lý thành công hay chưa
    private boolean isHandled;

    private TransportStatus transportStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;


    public static TransportHandleDTO convertEntityToDTO(TransportHandle transportHandles) {
        TransportHandleDTO transportHandleDTO = new TransportHandleDTO();
        transportHandleDTO.setTransportHandleId(transportHandles.getTransportHandleId());
        transportHandleDTO.setUsername(transportHandles.getUsername());
        transportHandleDTO.setWardCode(transportHandles.getWardCode());
        transportHandleDTO.setWardName(IWardService.getWardNameByWardCode(transportHandles.getWardCode()));
        transportHandleDTO.setActive(transportHandles.isActive());
        transportHandleDTO.setHandled(transportHandles.isHandled());
        transportHandleDTO.setTransportStatus(transportHandles.getTransportStatus());
        transportHandleDTO.setCreateAt(transportHandles.getCreateAt());
        transportHandleDTO.setUpdateAt(transportHandles.getUpdateAt());
        return transportHandleDTO;
    }


    public static List<TransportHandleDTO> convertEntitiesToDTOs(List<TransportHandle> transportHandles) {
        List<TransportHandleDTO> transportHandleDTOs = new ArrayList<>();
        if (transportHandles != null) {
            transportHandles.sort((o1, o2) -> o1.getCreateAt().compareTo(o2.getCreateAt()));

            for (TransportHandle transportHandle : transportHandles) {
                transportHandleDTOs.add(convertEntityToDTO(transportHandle));
            }
        }

        return transportHandleDTOs;
    }
}
