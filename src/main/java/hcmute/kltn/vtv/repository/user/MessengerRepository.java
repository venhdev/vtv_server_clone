package hcmute.kltn.vtv.repository.user;

import hcmute.kltn.vtv.model.entity.user.Messenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessengerRepository extends JpaRepository<Messenger, Long> {

    Optional<List<Messenger>> findAllByRomChatRomChatId(Long id);

}
