package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSaver {

    public void saveCommitmentMessage(UserEntity user, String message) {
        user.updateCommitmentMessage(message);
    }
}
