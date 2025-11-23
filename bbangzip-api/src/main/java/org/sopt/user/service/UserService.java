package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.dto.req.CommitmentMessageCreateReq;
import org.sopt.user.dto.req.UserProfileUpdateReq;
import org.sopt.user.dto.res.CommitmentMessageRes;
import org.sopt.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserFacade userFacade;

    @Transactional
    public CommitmentMessageRes createCommitmentMessage(
            final long userId,
            final CommitmentMessageCreateReq commitmentMessageCreateReq
    ) {
        User user = userFacade.getUserById(userId);
        User updated = userFacade.saveCommitmentMessage(user, commitmentMessageCreateReq.commitmentMessage());

        return new CommitmentMessageRes(updated.getCommitmentMessage());
    }

    @Transactional
    public User updateProfile(final long userId, final UserProfileUpdateReq request) {
        return userFacade.updateProfile(
                userId,
                request.profileImageKey(),
                request.nickname(),
                request.commitmentMessage()
        );
    }

    public User getProfile(final Long userId) {
        return userFacade.getProfile(userId);
    }

}
