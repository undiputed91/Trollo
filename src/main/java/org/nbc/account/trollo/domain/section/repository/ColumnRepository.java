package org.nbc.account.trollo.domain.section.repository;

import org.nbc.account.trollo.domain.section.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Section, Long> {

}
