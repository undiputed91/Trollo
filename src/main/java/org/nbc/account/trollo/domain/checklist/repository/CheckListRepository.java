package org.nbc.account.trollo.domain.checklist.repository;

import org.nbc.account.trollo.domain.checklist.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

}
