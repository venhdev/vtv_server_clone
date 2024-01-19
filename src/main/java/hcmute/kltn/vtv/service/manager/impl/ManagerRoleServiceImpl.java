package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.model.data.manager.response.ManagerResponse;
import hcmute.kltn.vtv.model.dto.user.ManagerDTO;
import hcmute.kltn.vtv.model.entity.manager.Manager;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.extra.Role;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.manager.ManagerRepository;
import hcmute.kltn.vtv.repository.user.CustomerRepository;
import hcmute.kltn.vtv.service.manager.IManagerRoleService;
import hcmute.kltn.vtv.service.user.impl.CustomerServiceImpl;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ManagerRoleServiceImpl implements IManagerRoleService {


    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private ManagerRepository managerRepository;


    @Override
    @Transactional
    public ManagerResponse managerAddRole(String usernameAdded, String usernameCustomer, Role role) {

        checkRoleAdd(role);

        if (managerRepository.existsByManagerUsernameAndStatus(usernameCustomer, Status.ACTIVE)) {
            throw new BadRequestException("Nhân viên đã có quyền quản lý!");
        }

        Customer customer = customerService.getCustomerByUsername(usernameCustomer);
        customer.addRole(role);

        Manager manager = checkManager(usernameAdded, customer);

        try {
            managerRepository.save(manager);
            customerRepository.save(customer);

            String message = getMessageByRole(role);

            return managerResponse(manager, message, "Success");
        } catch (Exception e) {
            throw new BadRequestException("Thêm quyền quản lý thất bại!" + e.getMessage());
        }
    }

    private String getMessageByRole(Role role) {
        if (role.equals(Role.MANAGER_SHIPPING)) {
            return "Thêm quyền quản lý vận chuyển thành công!";
        } else if (role.equals(Role.MANAGER_VENDOR)) {
            return "Thêm quyền quản lý cửa hàng thành công!";
        } else {
            return "Thêm quyền quản lý khách hàng thành công!";
        }
    }


    private void checkRoleAdd(Role role) {
        if (!role.equals(Role.MANAGER_SHIPPING) && !role.equals(Role.MANAGER_VENDOR) && !role.equals(Role.MANAGER_CUSTOMER)) {
            throw new BadRequestException("Quyền quản lý không hợp lệ! Quyền quản lý phải là MANAGER_SHIPPING, MANAGER_VENDOR hoặc MANAGER_CUSTOMER.");
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

    private Manager checkManager(String usernameAdded, Customer customer) {

        Manager manager = new Manager();

        if (managerRepository.existsByManagerUsername(customer.getUsername())) {
            manager = managerRepository.findByManagerUsername(customer.getUsername())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy quản lý!"));
            if (!manager.getUsernameAdded().equals(usernameAdded)) {
                throw new BadRequestException("Bạn không có quyền thêm quyền quản lý cho nhân viên này!");
            }
        } else {
            manager.setUsernameAdded(usernameAdded);
            manager.setManager(customer);
            manager.setCreateAt(LocalDateTime.now());
        }
        manager.setUpdateAt(LocalDateTime.now());
        manager.setStatus(Status.ACTIVE);

        return manager;
    }


}
