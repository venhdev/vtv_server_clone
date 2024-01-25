package hcmute.kltn.vtv.controller.manager;


import hcmute.kltn.vtv.model.data.manager.request.TransportProviderRegisterRequest;
import hcmute.kltn.vtv.model.data.shipping.response.TransportProviderResponse;
import hcmute.kltn.vtv.service.manager.IManagerTransportProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/transport-provider")
public class ManagerTransportProviderController {


    private final IManagerTransportProvider managerTransportProvider;

    @PostMapping("/add")
        public ResponseEntity<TransportProviderResponse> addNewTransportProvider(@RequestBody TransportProviderRegisterRequest request,
                                                                             HttpServletRequest servletRequest) {
        String usernameAdded = (String) servletRequest.getAttribute("username");
        request.setUsernameAdded(usernameAdded);
        request.validate();

        return ResponseEntity.ok(managerTransportProvider.addNewTransportProvider(request));
    }


}
