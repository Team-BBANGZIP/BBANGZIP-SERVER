package org.sopt.userbread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.userbread.domain.UserBreadEntity;
import org.sopt.userbread.repository.UserBreadRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserBreadSaver {

    private final UserBreadRepository userBreadRepository;

    @Transactional
    public UserBreadEntity save(UserBreadEntity entity) {
        return userBreadRepository.save(entity);
    }

}