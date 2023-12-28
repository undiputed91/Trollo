package org.nbc.account.trollo.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimestampEntity {

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createTimestamp;

    @Column
    @UpdateTimestamp
    private Timestamp updateTimestamp;

}
