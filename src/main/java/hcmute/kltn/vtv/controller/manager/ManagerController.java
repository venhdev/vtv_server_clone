package hcmute.kltn.vtv.controller.manager;


import hcmute.kltn.vtv.model.data.manager.response.ListManagerPageResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.service.manager.IManagerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final IManagerService managerService;

    @GetMapping("/info")
    public ResponseEntity<ManagerResponse> getManagerInfo(HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(managerService.getManagerByUserName(username));
    }

    @GetMapping("/list/username-added/{usernameAdded}/status/{status}")
    public ResponseEntity<ListManagerPageResponse> getManagersByUsernameAddedAndStatus(@PathVariable String usernameAdded,
                                                                                       @PathVariable Status status,
                                                                                       @RequestParam int page,
                                                                                       @RequestParam int size) {
        managerService.checkRequestPageParams(page, size);

        return ResponseEntity.ok(managerService.getManagersByUsernameAddedAndStatus(usernameAdded, status, page, size));
    }

    @GetMapping("/list/status/{status}")
    public ResponseEntity<ListManagerPageResponse> getListManagerByStatus(@PathVariable Status status,
                                                                           @RequestParam int page,
                                                                           @RequestParam int size) {
        managerService.checkRequestPageParams(page, size);

        return ResponseEntity.ok(managerService.listManagerPageResponseByStatus(status, page, size));
    }

    @DeleteMapping("/delete/id/{managerId}")
    public ResponseEntity<ManagerResponse> deleteManagerByrId(@PathVariable Long managerId,
                                                         HttpServletRequest servletRequest) {
        String username = (String) servletRequest.getAttribute("username");

        return ResponseEntity.ok(managerService.deleteManager(username, managerId));
    }


}
