package org.sopt.user.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRetriever userRetriever;
    private final UserUpdater userUpdater;

    public User getUserById(final long userId) {
        return userRetriever.findByUserId(userId);
    }

    public User saveCommitmentMessage(User user, String message) {
        return userUpdater.updateCommitmentMessage(user, message);
    }

    public UserEntity findByIdForUpdate(final long userId){
        return userUpdater.findByIdForUpdate(userId);
    }
}
