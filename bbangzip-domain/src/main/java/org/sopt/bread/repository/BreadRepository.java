package org.sopt.bread.repository;

import org.sopt.bread.domain.BreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BreadRepository extends JpaRepository<BreadEntity, Long> {

    Optional<BreadEntity> findByName(String name);

}