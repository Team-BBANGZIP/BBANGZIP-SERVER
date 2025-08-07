package org.sopt.user.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.exception.UserCoreErrorCode;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserRetriever {

    private final UserRepository userRepository;

    public UserEntity findByUserId(final long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserCoreErrorCode.USER_NOT_FOUND));
    }
}
