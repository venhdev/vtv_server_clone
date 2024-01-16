package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.model.data.manager.request.DeliverRequest;
import hcmute.kltn.vtv.model.data.manager.response.DeliverResponse;
import hcmute.kltn.vtv.model.dto.shipping.DeliverDTO;
import hcmute.kltn.vtv.model.entity.location.District;
import hcmute.kltn.vtv.model.entity.location.Ward;
import hcmute.kltn.vtv.model.entity.shipping.Deliver;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.shipping.DeliverRepository;
import hcmute.kltn.vtv.service.location.IDistrictService;
import hcmute.kltn.vtv.service.location.IWardService;
import hcmute.kltn.vtv.service.manager.IManagerDeliverService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManagerDeliverServiceImpl implements IManagerDeliverService {

    @Autowired
    private DeliverRepository deliverRepository;
    @Autowired
    private IDistrictService districtService;
    @Autowired
    private IWardService wardService;
    @Autowired
    private ICustomerService customerService;


    @Override
    @Transactional
    public DeliverResponse addNewDeliver(DeliverRequest request) {

        District district = districtService.getDistrictByCode(request.getDistrictCodeWork());
        List<Ward> wards = wardService.getWardsByWardsCodeWithDistrictCode(request.getWardsCodeWork(), request.getDistrictCodeWork());
        Customer customer = checkCustomerById(request.getCustomerId());


        Deliver deliver = DeliverRequest.convertRequestToEntity(request);

        deliver.setDistrictWork(district);
        deliver.setWardsWork(wards);
        deliver.setCustomer(customer);

        deliver.setStatus(Status.ACTIVE);
        deliver.setCreateAt(LocalDateTime.now());
        deliver.setUpdateAt(LocalDateTime.now());

        System.out.println(district);


        try {
          deliverRepository.save(deliver);

            String message = "Thêm mới nhân viên " + getTypeWork(deliver.getTypeWork()) + " thành công.";

            return deliverResponse(deliver, message, "OK");
        } catch (Exception e) {
            throw new InternalServerErrorException("Thêm mới nhân viên " + getTypeWork(deliver.getTypeWork()) + " thất bại." + e.getMessage());
        }
    }

    private Customer checkCustomerById(Long customerId) {

        if (deliverRepository.existsByCustomerCustomerId(customerId)) {
            throw new NotFoundException("Nhân viên này đã tồn tại.");
        }

        return customerService.getCustomerById(customerId);
    }


    private String getTypeWork(String typeWork) {
        return switch (typeWork) {
            case "shipper" -> "giao hàng";
            case "shipper-shop" -> "lấy hàng tại cửa hàng";
            case "shipper-warehouse" -> "kho hàng";
            default -> "trung chuyển";
        };

    }


    public DeliverResponse deliverResponse(Deliver deliver, String message, String status) {
        DeliverResponse response = new DeliverResponse();
        response.setDeliverDTO(DeliverDTO.convertEntityToDTO(deliver));
        response.setCode(200);
        response.setMessage(message);
        response.setStatus(status);

        return response;
    }


}
