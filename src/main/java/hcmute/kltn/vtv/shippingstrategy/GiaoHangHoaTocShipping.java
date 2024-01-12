package hcmute.kltn.vtv.shippingstrategy;

public class GiaoHangHoaTocShipping implements IShipping{
    @Override
    public long calculateShippingCost(Long total) {
        if (total >= 500000) {
            return 0;
        }
        return 0;
    }
}

