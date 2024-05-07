package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.model.data.manager.response.ShopsResponse;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.manager.response.ListShopManagerResponse;
import hcmute.kltn.vtv.model.data.manager.response.ManagerShopResponse;
import hcmute.kltn.vtv.model.dto.vtv.ShopDTO;
import hcmute.kltn.vtv.model.entity.vendor.Shop;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.repository.vtv.CategoryRepository;
import hcmute.kltn.vtv.repository.vtv.ShopRepository;
import hcmute.kltn.vtv.repository.manager.ManagerShopRepository;
import hcmute.kltn.vtv.service.manager.IManagerProductService;
import hcmute.kltn.vtv.service.manager.IManagerShopService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import hcmute.kltn.vtv.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerShopServiceImpl implements IManagerShopService {

    private final ShopRepository shopRepository;
    private final ICustomerService customerService;
    private final ManagerShopRepository managerShopRepository;
    private final IManagerProductService managerProductService;
    private final CategoryRepository categoryRepository;

    @Override
    public ManagerShopResponse getShopById(Long id) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng!"));

        return ManagerShopResponse.managerShopResponse(shop, "Lấy thông tin quản lý cửa hàng thành công!", "OK");
    }


    @Override
    public ShopsResponse getShopsByStatus(int page, int size, Status status){
        Page<Shop> shops = shopRepository.findAllByStatusOrderByName(status, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng!"));
        String message = "Lấy danh sách cửa hàng theo trạng thái " + messageByStatus(status) + " thành công!";

        return ShopsResponse.shopsResponse(shops, message, page, size);
    }


    @Override
    public ShopsResponse getShopsByNameAndStatus(int page, int size, String name, Status status){
        Page<Shop> shops = shopRepository.findAllByNameContainsAndStatusOrderByName(name, status, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng!"));
        String message = "Lấy danh sách cửa hàng theo tên và trạng thái " + messageByStatus(status) + " thành công!";

        return ShopsResponse.shopsResponse(shops, message, page, size);
    }




    public ManagerShopResponse lockShopById(Long shopId, String username, String note) {
        // Customer customer = customerService.getCustomerByUsername(username);
        // Shop shop = checkShopLock(shopId);
        // ManagerShop managerShop = new ManagerShop();
        // if(managerShopRepository.existsByShop_ShopId(shopId)){
        // managerShop = managerShopRepository.findByShop_ShopId(shopId).
        // orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng!"));
        // if (managerShop.isLock()) {
        // throw new NotFoundException("Cửa hàng đã bị khóa!");
        // }
        // }else {
        // managerShop.setShop(shop);
        // managerShop.setManager(customer);
        // }
        // managerShop.setLock(true);
        // managerShop.setDelete(false);
        // managerShop.setNote(note);
        //
        //
        //
        //
        // try {
        // ///managerShopRepository.save(managerShop);
        // }
        // catch (Exception e){
        // throw new NotFoundException("Khóa cửa hàng thất bại!");
        // }

        return null;
    }

    private Shop checkShopLock(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cửa hàng!"));
        if (shop.getStatus().equals(Status.DELETED)) {
            throw new NotFoundException("Cửa hàng đã bị xóa!");
        }

        return shop;
    }

    public ManagerShopResponse unLockShopById(Long shopId, String username) {

        return null;
    }

    @Override
    public ListShopManagerResponse getListShop(int size, int page, Status status) {
        int totalShop = shopRepository.countAllByStatus(status);
        int totalPage = (int) Math.ceil((double) totalShop / size);

        Page<Shop> shops = shopRepository.findAllByStatusOrderByName(status, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new BadRequestException("Không tìm thấy danh sách cửa hàng"));

        return listShopManagerResponse(shops.getContent(),
                size, page, totalPage, "Lấy danh sách cửa hàng theo trạng thái thành công!");
    }

    @Override
    public ListShopManagerResponse getListShopByName(int size, int page, String name, Status status) {
        int totalShop = shopRepository.countByNameContainsAndStatus(name, status);
        int totalPage = (int) Math.ceil((double) totalShop / size);

        Page<Shop> shops = shopRepository
                .findAllByNameContainsAndStatusOrderByName(name, status, PageRequest.of(page - 1, size))
                .orElseThrow(() -> new BadRequestException("Không tìm thấy danh sách cửa hàng"));

        return listShopManagerResponse(shops.getContent(),
                size, page, totalPage, "Lấy danh sách cửa hàng theo tên thành công!");
    }

    public ListShopManagerResponse listShopManagerResponse(List<Shop> shops, int size, int page, int totalPage,
            String message) {
        ListShopManagerResponse response = new ListShopManagerResponse();
        response.setShopDTOs(ShopDTO.convertEntitiesToDTOs(shops));
        response.setSize(size);
        response.setPage(page);
        response.setTotalPage(totalPage);
        response.setCount(shops.size());
        response.setMessage(message);
        response.setCode(200);
        response.setStatus("OK");

        return response;
    }


    private String messageByStatus(Status status) {
        if (status.equals(Status.ACTIVE)) {
            return "hoạt động";
        } else if (status.equals(Status.INACTIVE)) {
            return "ngưng hoạt động";
        } else if (status.equals(Status.LOCKED)) {
            return "bị khóa";
        } else {
            return "đã xóa";
        }
    }



}
