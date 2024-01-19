package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.model.data.manager.response.ListManagerPageResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.dto.manager.ManagerDTO;
import hcmute.kltn.vtv.model.entity.manager.Manager;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.manager.ManagerRepository;
import hcmute.kltn.vtv.service.manager.IManagerRoleService;
import hcmute.kltn.vtv.service.manager.IManagerService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private final IManagerRoleService managerRoleService;
    @Autowired
    private final ManagerRepository managerRepository;


    @Override
    public ManagerResponse getManagerByUserName(String username) {
        Manager manager = managerRepository.findByManagerUsername(username)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quản lý!"));

        if (!manager.getStatus().equals(Status.ACTIVE)) {
            throw new BadRequestException("Tài khoản của bạn không khả dụng!");
        }
        String message = "Lấy quản lý thành công!";
        return managerResponse(manager, message, "OK ");
    }




    private ManagerResponse managerResponse(Manager manager, String message, String status) {
        ManagerResponse managerResponse = new ManagerResponse();
        managerResponse.setManagerDTO(ManagerDTO.convertEntityToDTO(manager));
        managerResponse.setStatus(status);
        managerResponse.setCode(200);
        managerResponse.setMessage(message);
        return managerResponse;
    }

    private ListManagerPageResponse listManagerPageResponse(List<Manager> managers, String message,
                                                            int totalPage, int page, int size) {
        ListManagerPageResponse listManagerPageResponse = new ListManagerPageResponse();
        listManagerPageResponse.setTotalPage(totalPage);
        listManagerPageResponse.setPage(page);
        listManagerPageResponse.setSize(size);
        listManagerPageResponse.setCount(managers.size());
        listManagerPageResponse.setManagerDTOs(ManagerDTO.convertEntitiesToDTOs(managers));
        listManagerPageResponse.setStatus("OK");
        listManagerPageResponse.setCode(200);
        listManagerPageResponse.setMessage(message);

        return listManagerPageResponse;


    }

}
