package hcmute.kltn.vtv.repository.user;

import hcmute.kltn.vtv.model.entity.user.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, UUID> {



}
