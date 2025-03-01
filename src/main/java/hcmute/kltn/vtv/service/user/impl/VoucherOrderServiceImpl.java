package hcmute.kltn.vtv.service.user.impl;

import hcmute.kltn.vtv.service.user.IVoucherCustomerService;
import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.entity.user.Order;
import hcmute.kltn.vtv.model.entity.vendor.Voucher;
import hcmute.kltn.vtv.model.entity.user.VoucherOrder;
import hcmute.kltn.vtv.model.extra.VoucherType;
import hcmute.kltn.vtv.repository.user.VoucherOrderRepository;
import hcmute.kltn.vtv.repository.vtv.VoucherRepository;
import hcmute.kltn.vtv.service.manager.impl.ManagerVoucherSystemServiceImpl;
import hcmute.kltn.vtv.service.user.IVoucherOrderService;
import hcmute.kltn.vtv.service.vendor.impl.VoucherShopServiceImpl;
import hcmute.kltn.vtv.util.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoucherOrderServiceImpl implements IVoucherOrderService {

    private final VoucherOrderRepository voucherOrderRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherShopServiceImpl voucherShopService;
    private final ManagerVoucherSystemServiceImpl voucherSystemService;
    private final IVoucherCustomerService voucherCustomerService;

    @Transactional
    @Override
    public VoucherOrder saveVoucherOrder(Long voucherId, Order order, boolean isShop) {
        Voucher voucher;
        if (isShop) {
            voucher = voucherShopService.checkVoucherShop(voucherId, order.getShop().getShopId());

        } else {
            voucher = voucherSystemService.checkVoucherSystem(voucherId);
        }

        VoucherOrder voucherOrder = createVoucherOrder(voucher, order, isShop);

        try {
            voucherRepository.save(voucher);
            return voucherOrderRepository.save(voucherOrder);
        } catch (Exception e) {
            throw new InternalServerErrorException("Thêm mới mã giảm giá thất bại!");
        }
    }


    @Transactional
    @Override
    public VoucherOrder addNewVoucherOrderByCode(String code, Order order, Long shopId) {
        Voucher voucher;
        if (shopId != null) {
            voucher = voucherCustomerService.getVoucherByShopVoucherCodeAndShopId(code, shopId);

        } else {
            voucher = voucherCustomerService.getVoucherBySystemVoucherCode(code);
        }

        VoucherOrder voucherOrder = createVoucherOrder(voucher, order, shopId);
        try {
            voucherRepository.save(voucher);

            return voucherOrderRepository.save(voucherOrder);
        } catch (Exception e) {
            throw new InternalServerErrorException("Thêm mới mã giảm giá thất bại!");
        }
    }


    @Transactional
    @Override
    public VoucherOrder cancelVoucherOrder(Long voucherOrderId) {
        VoucherOrder voucherOrder = voucherOrderRepository.findById(voucherOrderId)
                .orElseThrow(() -> new BadRequestException("Mã giảm giá không tồn tại!"));

        Voucher voucher = voucherOrder.getVoucher();
        voucher.setQuantityUsed(voucher.getQuantityUsed() - 1);

        try {
            voucherRepository.save(voucher);
            return voucherOrder;
        } catch (Exception e) {
            throw new InternalServerErrorException("Cập nhật mã giảm giá thất bại!");
        }
    }

    @Override
    public int calculateVoucher(Long voucherId, Long shopId, Long totalPrice, boolean isShop) {
        Voucher voucher;
        if (isShop) {
            voucher = voucherShopService.checkVoucherShop(voucherId, shopId);
        } else {
            voucher = voucherSystemService.checkVoucherSystem(voucherId);
        }

//        if (voucher.getType().equals(VoucherType.SHIPPING)) {
//            return voucher.getDiscount();
//        }

        if (voucher.getType().equals(VoucherType.PERCENTAGE_SHOP)
                || voucher.getType().equals(VoucherType.PERCENTAGE_SYSTEM)) {
            return (int) (voucher.getDiscount() * totalPrice / 100);
        }

        return voucher.getDiscount();
    }



    @Override
    public VoucherOrder createVoucherOrder(String voucherCode, Long shopId) {
        Voucher voucher;
        if (shopId != null) {
            voucher = voucherCustomerService.getVoucherByShopVoucherCodeAndShopId(voucherCode, shopId);
        } else {
            voucher = voucherCustomerService.getVoucherBySystemVoucherCode(voucherCode);
        }
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setType(shopId != null);
        voucherOrder.setVoucher(voucher);
        return voucherOrder;
    }



    private VoucherOrder createVoucherOrder(Voucher voucher, Order order, boolean isShop) {
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setType(!isShop);
        voucherOrder.setVoucher(voucher);
        voucherOrder.setOrder(order);
        voucher.setQuantityUsed(voucher.getQuantityUsed() + 1);

        return voucherOrder;
    }


    private VoucherOrder createVoucherOrder(Voucher voucher, Order order, Long shopId) {
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setType(shopId != null);
        voucherOrder.setVoucher(voucher);
        voucherOrder.setOrder(order);
        voucher.setQuantityUsed(voucher.getQuantityUsed() + 1);

        return voucherOrder;
    }

}
