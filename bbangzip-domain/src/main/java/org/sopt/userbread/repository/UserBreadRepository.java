package org.sopt.userbread.repository;

import org.sopt.userbread.domain.UserBreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBreadRepository extends JpaRepository<UserBreadEntity, Long> {

    @Query("""
        select distinct ub
        from UserBreadEntity ub
        join fetch ub.bread
        where ub.user.id = :userId
    """)
    List<UserBreadEntity> findAllByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndBreadId(Long userId, Long breadId);
}