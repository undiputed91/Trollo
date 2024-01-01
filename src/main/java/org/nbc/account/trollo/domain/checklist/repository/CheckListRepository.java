package org.nbc.account.trollo.domain.checklist.repository;

import java.util.List;
import java.util.Optional;
import org.nbc.account.trollo.domain.checklist.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    Optional<List<CheckList>> findByCardId(Long cardId);

    Optional<List<CheckList>> findByCardIdAndCheckSignIsTrue(Long cardId);
}
