package hcmute.kltn.vtv.controller.manager;

import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.extra.Role;
import hcmute.kltn.vtv.service.manager.IManagerRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/role")
@RequiredArgsConstructor
public class ManagerRoleController {


    private final IManagerRoleService managerRoleService;


    @PostMapping("/add")
    public ResponseEntity<ManagerResponse> managerAddRole(@RequestParam String usernameCustomer,
                                                          @RequestParam Role role,
                                                          HttpServletRequest servletRequest) {
        String usernameAdded = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(managerRoleService.managerAddRole(usernameAdded, usernameCustomer.trim(), role));
    }


}
