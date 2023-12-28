package org.nbc.account.trollo.domain.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Board {

    @Id
    @GeneratedValue
    private Long id;
}
