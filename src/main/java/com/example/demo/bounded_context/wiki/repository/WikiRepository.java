package com.example.demo.bounded_context.wiki.repository;

import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.solution.entity.Waste;
import com.example.demo.bounded_context.wiki.entity.Wiki;
import com.example.demo.bounded_context.wiki.entity.WikiState;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WikiRepository extends JpaRepository<Wiki, Long> {
    @Query("select w from Wiki w join fetch w.writer a where a.id = :writerId and w.waste.id = :wasteId and w.wikiState = 0")
    Optional<Wiki> existsPending(Long writerId, Long wasteId);

    @Query("select w from Wiki w join fetch w.waste t where t.id = :wasteId and w.wikiState = 1 order by w.modifiedDate desc")
    Optional<Wiki> findByRecent(Long wasteId);

    @Query("select w from Wiki w join fetch w.writer join fetch w.waste where w.id = :id")
    Optional<Wiki> findFetchById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Wiki w set w.writer = null where w.writer = :account")
    void updateWriter(Account account);

    @Query("select w from Wiki w where w.writer.id = :accountId and w.wikiState = :state")
    Page<Wiki> findByAccountIdAndStateWithPaging(Long accountId, WikiState state, Pageable pageable);
}
