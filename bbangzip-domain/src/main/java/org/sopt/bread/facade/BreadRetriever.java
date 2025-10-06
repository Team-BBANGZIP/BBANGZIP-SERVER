package org.sopt.bread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.bread.domain.BreadEntity;
import org.sopt.bread.repository.BreadRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BreadRetriever {

    private final BreadRepository breadRepository;

    public List<BreadEntity> findAll() {
        return breadRepository.findAll();
    }

    public Optional<BreadEntity> findByName(String name) {
        return breadRepository.findByName(name);
    }
}