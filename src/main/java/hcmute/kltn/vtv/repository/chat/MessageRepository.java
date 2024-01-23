package hcmute.kltn.vtv.repository.chat;

import hcmute.kltn.vtv.model.entity.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    Optional<Page<Message>> findByRoomChatRomChatIdOrderByDateDesc(UUID roomChatId, Pageable pageable);

    Optional<Page<Message>> findByRoomChatRomChatId(UUID roomChatId, Pageable pageable);





}
