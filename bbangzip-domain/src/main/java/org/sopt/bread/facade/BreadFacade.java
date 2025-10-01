package org.sopt.bread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.bread.domain.BreadEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BreadFacade {

    private final BreadRetriever breadRetriever;

    @Transactional(readOnly = true)
    public List<BreadEntity> findAll() {
        return breadRetriever.findAll()
                .stream()
                .sorted(Comparator.comparing(BreadEntity::getId))
                .toList();
    }

}