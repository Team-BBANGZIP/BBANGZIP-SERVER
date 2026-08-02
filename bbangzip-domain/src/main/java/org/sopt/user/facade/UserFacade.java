package org.sopt.user.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.jwt.auth.authentication.UserRole;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.type.RegisterStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRetriever userRetriever;
    private final UserUpdater userUpdater;
    private final UserSaver userSaver;
    private final UserRemover userRemover;

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

    public User getProfile(final long userId) {
        return userRetriever.findByUserId(userId);
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
    public UserEntity getOrCreateSocialUser(
            final String provider,
            final String providerId,
            final UserRole userRole
    ) {
        Optional<UserEntity> existing =
                userRetriever.findByProviderAndProviderId(provider, providerId);

        if (existing.isPresent()) {
            UserEntity user = existing.get();
            if (Boolean.TRUE.equals(user.getIsDeleted())) {
                user.revertDeleteUser();
            }
            return user;
        }

        return createSocialUserSafely(provider, providerId, userRole);
    }

    private UserEntity createSocialUserSafely(
            final String provider,
            final String providerId,
            final UserRole userRole
    ) {
        try {
            UserEntity user = UserEntity.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .registerStatus(RegisterStatus.SOCIAL_LOGIN_COMPLETED)
                    .userRole(userRole)
                    .build();
            return userSaver.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            return userRetriever.findByProviderAndProviderId(provider, providerId)
                    .map(user -> {
                        if (Boolean.TRUE.equals(user.getIsDeleted())) {
                            user.revertDeleteUser();
                        }
                        return user;
                    })
                    .orElseThrow(() -> new IllegalStateException(
                            "소셜 로그인 사용자 생성 충돌 후 재조회에 실패했습니다.",
                            e
                    ));
        }
    }

    @Transactional
    public void updateRegisterStatus(long userId, RegisterStatus status) {
        userUpdater.updateRegisterStatus(userId, status);
    }

    public void deleteByUserId(long userId){
        userRemover.deleteUserById(userId);
    }
}
