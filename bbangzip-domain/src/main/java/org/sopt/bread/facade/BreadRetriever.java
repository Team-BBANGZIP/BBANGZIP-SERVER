package org.sopt.bread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.bread.domain.BreadEntity;
import org.sopt.bread.repository.BreadRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BreadRetriever {

    private final BreadRepository breadRepository;

    public List<BreadEntity> findAll() {
        return breadRepository.findAll();
    }

}