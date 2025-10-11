package org.sopt.userbread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.userbread.repository.UserBreadRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserBreadRemover {

    private final UserBreadRepository userBreadRepository;

    public void deleteAllByUserId(Long userId) {
        userBreadRepository.deleteAllByUserId(userId);
    };

}