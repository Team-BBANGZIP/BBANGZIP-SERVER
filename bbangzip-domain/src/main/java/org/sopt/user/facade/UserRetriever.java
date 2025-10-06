package org.sopt.user.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.exception.UserCoreErrorCode;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserRetriever {

    private final UserRepository userRepository;

    public User findByUserId(final long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserCoreErrorCode.USER_NOT_FOUND));

        return userEntity.toDomain();
    }

    public UserEntity findUserEntityByUserId(final long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserCoreErrorCode.USER_NOT_FOUND));
    }

    public Optional<UserEntity> findByProviderAndProviderId(final String provider, final String providerId){
        return userRepository.findByProviderAndProviderId(provider, providerId);
    };

}
