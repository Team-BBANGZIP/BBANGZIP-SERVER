package org.sopt.user.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.type.RegisterStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRetriever userRetriever;
    private final UserUpdater userUpdater;
    private final UserSaver userSaver;

    public User getUserById(final long userId) {
        return userRetriever.findByUserId(userId);
    }
    public UserEntity getUserEntityById(final long userId) {
        return userRetriever.findUserEntityByUserId(userId);
    }

    public User saveCommitmentMessage(User user, String message) {
        return userUpdater.updateCommitmentMessage(user, message);
    }

    public User updateProfile(final long userId,
                              final Integer profileImageKey,
                              final String nickname,
                              final String commitmentMessage) {
        return userUpdater.updateProfile(userId, profileImageKey, nickname, commitmentMessage);
    }

    public UserEntity findByIdForUpdate(final long userId){
        return userUpdater.findByIdForUpdate(userId);
    }

    public Optional<UserEntity> getByProviderAndProviderId(final String provider, final String providerId) {
        return userRetriever.findByProviderAndProviderId(provider, providerId);
    }

    public UserEntity save(final UserEntity user){
        return userSaver.save(user);
    }

    @Transactional
    public void updateRegisterStatus(long userId, RegisterStatus status) {
        userUpdater.updateRegisterStatus(userId, status);
    }
}
