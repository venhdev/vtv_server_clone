package hcmute.kltn.vtv.controller.manager;

import hcmute.kltn.vtv.model.data.manager.request.DeliverRequest;
import hcmute.kltn.vtv.model.data.manager.response.DeliverResponse;
import hcmute.kltn.vtv.service.manager.IManagerDeliverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/deliver")
@RequiredArgsConstructor
public class ManagerDeliverController {

    @Autowired
    private IManagerDeliverService managerDeliverService;

    @PostMapping("/add")
    public ResponseEntity<DeliverResponse> addNewDeliver(@RequestBody DeliverRequest request,
                                                         HttpServletRequest servletRequest) {

        String username = (String) servletRequest.getAttribute("username");
        request.setUsernameAdded(username);
        request.validate();

        return ResponseEntity.ok(managerDeliverService.addNewDeliver(request));
    }

}
