package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.model.data.manager.response.ListManagerPageResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.dto.manager.ManagerDTO;
import hcmute.kltn.vtv.model.entity.manager.Manager;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.manager.ManagerRepository;
import hcmute.kltn.vtv.service.manager.IManagerRoleService;
import hcmute.kltn.vtv.service.manager.IManagerService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements IManagerService {

    private final IManagerRoleService managerRoleService;
    private final ManagerRepository managerRepository;
    private final ICustomerService customerService;


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

    @Override
    public ListManagerPageResponse getManagersByUsernameAddedAndStatus(String usernameAdded, Status status, int page, int size) {

        checkUsernameAdded(usernameAdded);

        Page<Manager> pageManager = managerRepository
                .findAllByUsernameAddedAndStatus(usernameAdded, status, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quản lý theo tên người đăng ký và trạng thái!"));


        String message = "Lấy danh sách quản lý theo tên người đăng ký và theo trạng " + status + " thành công!";

        return listManagerPageResponse(pageManager.getContent(), message, pageManager.getTotalPages(), page, size);

    }


    @Override
    public ListManagerPageResponse listManagerPageResponseByStatus(Status status, int page, int size) {


        Page<Manager> pageManager = managerRepository
                .findAllByStatusOrderByManagerFullName(status, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quản lý theo trạng thái!"));

        return listManagerPageResponse(pageManager.getContent(), "Lấy danh sách quản lý theo trạng thái thành công!",
                pageManager.getTotalPages(), page, size);
    }


    @Override
    @Transactional
    public ManagerResponse deleteManager(String usernameAdded, Long managerId) {

        Manager manager = checkManagerId(managerId);
        checkRoleUsernameAdded(usernameAdded, manager.getUsernameAdded());

        manager.setStatus(Status.INACTIVE);
        manager.setUpdateAt(LocalDateTime.now());
        try {
            customerService.removeAllRoleManagerOfCustomer(manager.getManager());
            managerRepository.save(manager);
            String message = "Xóa quản lý thành công!";
            return managerResponse(manager, message, "Deleted");
        } catch (Exception e) {
            throw new InternalServerErrorException("Xóa quản lý thất bại!");
        }
    }




    private void checkRoleUsernameAdded(String usernameAdded, String managerUsernameAdded) {
        if (!managerUsernameAdded.equals(usernameAdded)) {
            throw new BadRequestException("Bạn không có quyền thực hiện hành động này!");
        }
    }

    private Manager checkManagerId(Long managerId) {
        return managerRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quản lý!"));
    }

    private void checkUsernameAdded(String usernameAdded) {
        if (!managerRepository.existsByUsernameAdded(usernameAdded)) {
            throw new BadRequestException("Tài khoản người đăng ký của bạn không tồn tại!");
        }
    }


    @Override
    public void checkRequestPageParams(int page, int size) {
        if (page <= 0) {
            throw new BadRequestException("Trang phải lớn hơn 0!");
        }
        if (size <= 0) {
            throw new BadRequestException("Kích thước trang phải lớn hơn 0!");
        }
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

        ListManagerPageResponse response = new ListManagerPageResponse();
        response.setTotalPage(totalPage);
        response.setPage(page);
        response.setSize(size);
        response.setCount(managers.size());
        response.setManagerDTOs(ManagerDTO.convertEntitiesToDTOs(managers));
        response.setStatus("OK");
        response.setCode(200);
        response.setMessage(message);

        return response;


    }

}
