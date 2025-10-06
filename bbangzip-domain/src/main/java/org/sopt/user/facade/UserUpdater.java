package org.sopt.user.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.type.DefaultProfileImage;
import org.sopt.user.exception.UserCoreErrorCode;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UserUpdater {

    private final UserRepository userRepository;

    public User updateCommitmentMessage(User user, String message) {
        UserEntity userEntity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(UserCoreErrorCode.USER_NOT_FOUND));
        userEntity.updateCommitmentMessage(message);
        userRepository.save(userEntity);
        return userEntity.toDomain();
    }


    @Transactional
    public User updateProfile(final long userId,
                              final Integer profileImageKey,
                              final String nickname,
                              final String commitmentMessage) {

        UserEntity entity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserCoreErrorCode.USER_NOT_FOUND));

        String imageUrl = null;
        if (profileImageKey != null) {
            imageUrl = DefaultProfileImage.getUrlByKey(profileImageKey);
        }

        entity.updateProfile(nickname, imageUrl, commitmentMessage);
        return entity.toDomain();
    }


    public UserEntity findByIdForUpdate(final Long userId){
        return userRepository.findByIdForUpdate(userId)
                .orElseThrow(() -> new UserNotFoundException(UserCoreErrorCode.USER_NOT_FOUND));
    }

}
