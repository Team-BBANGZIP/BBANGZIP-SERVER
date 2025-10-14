package org.sopt.dailybaking.repository;


import org.sopt.dailybaking.domain.DailyBakingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DailyBakingRepository extends JpaRepository<DailyBakingEntity, Long> {

    @Query("""
        select d from DailyBakingEntity d
        where d.user.id = :userId
          and d.bakeDate >= :startOfDay
          and d.bakeDate <  :endOfDay
    """)
    Optional<DailyBakingEntity> findByUserIdAndBakeDateInDay(
            @Param("userId") Long userId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    void deleteAllByUserId(Long userId);

}