package org.sopt.bread.repository;

import org.sopt.bread.domain.BreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreadRepository extends JpaRepository<BreadEntity, Long> {
}