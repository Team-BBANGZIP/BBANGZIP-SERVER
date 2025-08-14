package org.sopt.user.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRetriever userRetriever;
    private final UserSaver userSaver;

    public User getUserById(final long userId) {
        UserEntity userEntity = userRetriever.findByUserId(userId);
        return User.fromEntity(userEntity);
    }

    public void saveCommitmentMessage(User user, String message) {
        UserEntity userEntity = userRetriever.findByUserId(user.getPlatformUserId());
        userEntity.updateCommitmentMessage(message); // UserEntity의 커밋 메시지 업데이트
        userSaver.save(userEntity);
    }
}
