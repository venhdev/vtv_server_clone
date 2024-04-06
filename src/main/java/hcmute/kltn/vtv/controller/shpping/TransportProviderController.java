package hcmute.kltn.vtv.controller.shpping;


import hcmute.kltn.vtv.model.data.shipping.request.UpdateTransportProviderRequest;
import hcmute.kltn.vtv.model.data.shipping.response.TransportProviderResponse;
import hcmute.kltn.vtv.service.shipping.ITransportProviderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping/transport-provider")
@RequiredArgsConstructor
public class TransportProviderController {

    private final ITransportProviderService transportProviderService;

    @PutMapping("/update")
    public ResponseEntity<TransportProviderResponse> updateTransportProvider(@RequestBody UpdateTransportProviderRequest request,
                                                                             HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");

        request.setUsername(username);
        request.validate();
        return ResponseEntity.ok(transportProviderService.updateTransportProviderResponse(request));
    }


}
