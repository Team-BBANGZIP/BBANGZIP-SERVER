package org.sopt.userbread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.userbread.domain.UserBreadEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBreadFacade {

    private final UserBreadRetriever userBreadRetriever;

    @Transactional(readOnly = true)
    public List<UserBreadEntity> findAllByUserId(Long userId) {
        return userBreadRetriever.findAllByUserId(userId);
    }

}