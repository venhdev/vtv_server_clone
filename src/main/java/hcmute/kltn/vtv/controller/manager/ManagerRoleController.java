package hcmute.kltn.vtv.controller.manager;

import hcmute.kltn.vtv.model.data.manager.response.ListManagerPageResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.extra.Role;
import hcmute.kltn.vtv.service.manager.IManagerRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<ManagerResponse> managerUpdateRole(@RequestParam String usernameCustomer,
                                                             @RequestParam Role role,
                                                             HttpServletRequest servletRequest) {
        String usernameAdded = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(managerRoleService.managerUpdateRole(usernameAdded, usernameCustomer.trim(), role));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ManagerResponse> managerDeleteRole(@RequestParam String usernameCustomer,
                                                             @RequestParam Role role,
                                                             HttpServletRequest servletRequest) {
        String usernameAdded = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(managerRoleService.managerDeleteRole(usernameAdded, usernameCustomer.trim(), role));
    }

    @GetMapping("/list")
    public ResponseEntity<ListManagerPageResponse> getListManagerPageByRole(@RequestParam int page,
                                                                             @RequestParam int size,
                                                                             @RequestParam Role role) {
        managerRoleService.checkRequestPageParams(page, size);

        return ResponseEntity.ok(managerRoleService.getListManagerPageByRole(role, page, size));
    }


}
