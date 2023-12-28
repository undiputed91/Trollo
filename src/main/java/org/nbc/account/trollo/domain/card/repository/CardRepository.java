package org.nbc.account.trollo.domain.card.repository;

import org.nbc.account.trollo.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
