package hcmute.kltn.vtv.service.shipping.shippingstrategy;

public interface IShippingStrategy {
    long calculateShippingCost(Long total);

    Long calculateShippingCost(int distanceLocation);


}
