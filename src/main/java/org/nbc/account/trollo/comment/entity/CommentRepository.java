package org.nbc.account.trollo.comment.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository <CommentEntity, Long> {

}
