package org.sopt.userbread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.userbread.domain.UserBreadEntity;
import org.sopt.userbread.repository.UserBreadRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBreadRetriever {

    private final UserBreadRepository userBreadRepository;

    public List<UserBreadEntity> findAllByUserId(Long userId) {
        return userBreadRepository.findAllByUserId(userId);
    }

    public boolean existsByUserIdAndBreadId(Long userId, Long breadId) {
        return userBreadRepository.existsByUserIdAndBreadId(userId, breadId);
    }
}