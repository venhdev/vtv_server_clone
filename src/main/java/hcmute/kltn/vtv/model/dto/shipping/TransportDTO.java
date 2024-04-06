package hcmute.kltn.vtv.model.dto.shipping;

import hcmute.kltn.vtv.model.entity.shipping.Transport;
import hcmute.kltn.vtv.model.extra.TransportStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
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

    private String wardCodeCustomer;

    private UUID orderId;

    private Long shopId;

    private String shippingMethod;

    private TransportStatus status;

    private int totalTransportHandle;

    private List<TransportHandleDTO> transportHandleDTOs;

    public static TransportDTO convertEntityToDTO(Transport transport) {
        TransportDTO transportDTO = new TransportDTO();
        transportDTO.setTransportId(transport.getTransportId());
        transportDTO.setWardCodeShop(transport.getWardCodeShop());
        transportDTO.setWardCodeCustomer(transport.getWardCodeCustomer());
        transportDTO.setOrderId(transport.getOrderId());
        transportDTO.setShopId(transport.getShopId());
        transportDTO.setShippingMethod(transport.getShippingMethod());
        transportDTO.setStatus(transport.getStatus());
        transportDTO.setTotalTransportHandle(transport.getTransportHandles().size());
        transportDTO.setTransportHandleDTOs(TransportHandleDTO.convertEntitiesToDTOs(transport.getTransportHandles()));
        return transportDTO;
    }

    public static List<TransportDTO> convertEntitiesToDTOs(List<Transport> transports) {
        transports.sort(Comparator.comparing(Transport::getCreateAt).reversed());
        List<TransportDTO> transportDTOs = new ArrayList<>();
        for (Transport transport : transports) {
            transportDTOs.add(convertEntityToDTO(transport));
        }
        return transportDTOs;
    }


}
