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
            final CommitmentMessageCreateReq request
    ) {
        User user = userFacade.getUserById(userId);
        userFacade.saveCommitmentMessage(user, request.commitmentMessage());

        return new CommitmentMessageRes(user.getCommitmentMessage());
    }
}
