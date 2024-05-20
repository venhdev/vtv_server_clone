package hcmute.kltn.vtv.service.manager.impl;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import hcmute.kltn.vtv.model.data.admin.request.VoucherSystemRequest;
import hcmute.kltn.vtv.model.data.admin.response.ListVoucherSystemResponse;
import hcmute.kltn.vtv.model.data.admin.response.VoucherSystemResponse;
import hcmute.kltn.vtv.model.dto.vtv.VoucherDTO;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.entity.vendor.Voucher;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.model.extra.VoucherType;
import hcmute.kltn.vtv.repository.vtv.VoucherRepository;
import hcmute.kltn.vtv.service.manager.IManagerVoucherSystemService;
import hcmute.kltn.vtv.service.user.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerVoucherSystemServiceImpl implements IManagerVoucherSystemService {

    private final VoucherRepository voucherRepository;
    private final ICustomerService customerService;

    @Override
    public VoucherSystemResponse addNewVoucherSystem(String username, VoucherSystemRequest request) {
        Customer customer = customerService.getCustomerByUsername(username);
        if (voucherRepository.existsByCodeAndShopNull(request.getCode())) {
            throw new BadRequestException("Mã giảm giá đã tồn tại trong hệ thống.");
        }
        Voucher voucher = VoucherSystemRequest.convertCreateToVoucher(request);
        voucher.setCustomer(customer);
        try {
            voucherRepository.save(voucher);

            return voucherAdminResponse(voucher, "Thêm mới mã giảm giá thành công.", "success");
        } catch (Exception e) {
            throw new BadRequestException("Thêm mới mã giảm giá thất bại!");
        }
    }

    @Override
    public VoucherSystemResponse getVoucherSystemByVoucherId(Long voucherId) {
        return voucherAdminResponse(getVoucherByVoucherId(voucherId), "Lấy mã giảm giá thành công.", "ok");
    }

    @Override
    public ListVoucherSystemResponse getListVoucherSystem(String username) {
        List<Voucher> vouchers = voucherRepository.findAllByShopNullAndStatusNot(Status.DELETED)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy mã giảm giá!"));

        return ListVoucherSystemResponse.listVoucherSystemResponse(vouchers, "Lấy danh sách mã giảm giá thành công.", username);
    }

    @Override
    public ListVoucherSystemResponse getListVoucherSystemByUsername(String username) {
        List<Voucher> vouchers = voucherRepository.findAllByCustomerUsernameAndStatusNot(username, Status.DELETED)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy mã giảm giá!"));

        return ListVoucherSystemResponse.listVoucherSystemResponse(vouchers, "Lấy danh sách mã giảm giá thành công.", username);
    }

    @Override
    public ListVoucherSystemResponse getListVoucherSystemByStatus(String username, Status status) {
        List<Voucher> vouchers = voucherRepository.findAllByShopNullAndStatus(status)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy mã giảm giá!"));

        return ListVoucherSystemResponse.listVoucherSystemResponse(vouchers, "Lấy danh sách mã giảm giá thành công.", username);
    }

    @Override
    public ListVoucherSystemResponse getListVoucherSystemByType(String username, VoucherType type) {
        List<Voucher> vouchers = voucherRepository.findAllByShopNullAndStatusNotAndType(Status.DELETED, type)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy mã giảm giá!"));

        return ListVoucherSystemResponse.listVoucherSystemResponse(vouchers, "Lấy danh sách mã giảm giá thành công.", username);
    }

    @Override
    @Transactional
    public VoucherSystemResponse updateVoucherSystem(Long voucherId, VoucherSystemRequest request, String username) {
        Voucher voucher = getVoucherSystemUpdate(voucherId, username);
        if (!voucher.getCode().equals(request.getCode()) && checkVoucherCode(request.getCode())) {
            throw new BadRequestException("Mã giảm giá đã tồn tại trong hệ thống.");
        }
        if (voucher.getStatus() == Status.DELETED) {
            throw new BadRequestException("Mã giảm giá đã bị xóa!");
        }

        if (voucher.getQuantityUsed() >= 0) {
            throw new BadRequestException("Mã giảm giá đã được sử dụng!");
        }

        VoucherSystemRequest.convertUpdateToVoucher(request, voucher);

        try {
            voucherRepository.save(voucher);

            return voucherAdminResponse(voucher, "Cập nhật mã giảm giá thành công.", "success");
        } catch (Exception e) {
            throw new BadRequestException("Cập nhật mã giảm giá thất bại!");
        }
    }

    @Override
    @Transactional
    public VoucherSystemResponse updateStatusVoucherSystem(Long voucherId, Status status, String username) {
        Voucher voucher = getVoucherSystemUpdate(voucherId, username);
        if (status != Status.ACTIVE && status != Status.INACTIVE && status != Status.DELETED
                && status != Status.CANCEL) {
            throw new BadRequestException("Trạng thái không hợp lệ!");
        }

        if (voucher.getStatus() == Status.DELETED) {
            throw new BadRequestException("Mã giảm giá đã bị xóa!");
        }
        voucher.setStatus(status);

        try {
            voucherRepository.save(voucher);

            return voucherAdminResponse(voucher, "Cập nhật trạng thái mã giảm giá thành công.", "success");
        } catch (Exception e) {
            throw new BadRequestException("Cập nhật trạng thái mã giảm giá thất bại!");
        }
    }

    @Override
    public Voucher checkVoucherSystem(Long voucherId) {
        Voucher voucher = getVoucherByVoucherId(voucherId);



        Instant now = Instant.now(); // Sử dụng Instant thay vì Date
        Instant voucherStartDate = voucher.getStartDate().toInstant(); // Chuyển startDate của voucher thành Instant
        Instant voucherEndDate = voucher.getEndDate().toInstant();

        if (voucherStartDate.isAfter(now)) {
            throw new BadRequestException("Mã giảm giá chưa có hiệu lực!");
        }

        if (voucherEndDate.isBefore(now)) {
            throw new BadRequestException("Mã giảm giá đã hết hạn!");
        }

        if (voucher.getStatus() == Status.DELETED) {
            throw new BadRequestException("Mã giảm giá đã bị xóa!");
        }

        if (voucher.getQuantityUsed() >= voucher.getQuantity()) {
            throw new BadRequestException("Mã giảm giá đã hết lượt sử dụng!");
        }

        return voucher;
    }

    private Voucher getVoucherSystemUpdate(Long voucherId, String username) {
        Voucher voucher = getVoucherByVoucherId(voucherId);
        if (!voucher.getCustomer().getUsername().equals(username)) {
            throw new BadRequestException("Bạn không có quyền cập nhật mã giảm giá này!");
        }
        return voucher;
    }

    private boolean checkVoucherCode(String code) {
        return voucherRepository.existsByCodeAndShopNull(code);
    }

    private VoucherSystemResponse voucherAdminResponse(Voucher voucher, String message, String status) {
        VoucherSystemResponse response = new VoucherSystemResponse();
        response.setVoucherDTO(VoucherDTO.convertEntityToDTO(voucher));
        response.setCode(200);
        response.setMessage(message);
        response.setStatus(status);
        response.setUsername(voucher.getCustomer().getUsername());

        return response;
    }



    private Voucher getVoucherByVoucherId(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy mã giảm giá!"));
        if (voucher == null) {
            throw new BadRequestException("Mã giảm giá không tồn tại!");
        }
        if (voucher.getCustomer() == null) {
            throw new BadRequestException("Mã giảm giá không thuộc quyền quản lý của hệ thống!");
        }

        return voucher;
    }

}
