package hcmute.kltn.vtv.repository;

import hcmute.kltn.vtv.model.entity.vtc.Address;
import hcmute.kltn.vtv.model.entity.vtc.Customer;
import hcmute.kltn.vtv.model.extra.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<List<Address>> findAllByCustomerAndStatusNot(Customer customer, Status status);

    Optional<Address> findFirstByCustomerUsernameAndStatus(String username, Status status);

    Optional<Address> findByAddressIdAndCustomerUsername(Long addressId, String username);

    Optional<List<Address>> findAllByCustomerAndStatus(Customer customer, Status status);

    Optional<List<Address>> findAllByCustomerAndStatusAndAddressIdNot(Customer customer, Status status, Long addressId);
}
