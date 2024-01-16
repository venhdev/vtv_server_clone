package hcmute.kltn.vtv.model.dto;

import hcmute.kltn.vtv.model.entity.vtc.Transport;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransportDTO {

    private UUID transportId;

    private String wardCodeShop;

    private Long orderId;

    private TransportStatus transportStatus;

    private List<TransportHandleDTO> transportHandleDTOs;

    public static TransportDTO convertEntityToDTO(Transport transport) {
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setTransportId(transport.getTransportId());
        transportDTO.setWardCodeShop(transport.getWardCodeShop());
        transportDTO.setTransportStatus(transport.getTransportStatus());
        transportDTO.setOrderId(transport.getOrder().getOrderId());
        transportDTO.setTransportHandleDTOs(TransportHandleDTO.convertEntitiesToDTOs(transport.getTransportHandles()));
        return transportDTO;
    }

    public static List<TransportDTO> convertEntitiesToDTOs(List<Transport> transports) {
        List<TransportDTO> transportDTOs = new ArrayList<>();
        if (transports != null) {
            transports.sort((o1, o2) -> o1.getCreateAt().compareTo(o2.getCreateAt()));
            for (Transport transport : transports) {
                transportDTOs.add(convertEntityToDTO(transport));
            }
        }

        return transportDTOs;
    }


}
