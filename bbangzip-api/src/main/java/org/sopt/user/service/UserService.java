package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.User;
import org.sopt.user.dto.req.CommitmentMessageCreateReq;
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
}
