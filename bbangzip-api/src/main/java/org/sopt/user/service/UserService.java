package org.sopt.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.dto.request.CommitmentMessageCreateRequest;
import org.sopt.user.dto.response.CommitmentMessageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRetriever userRetriever;
    private final UserSaver userSaver;

    @Transactional
    public CommitmentMessageResponse createCommitmentMessage(
            final Long userId,
            final CommitmentMessageCreateRequest request
    ) {
        UserEntity user = userRetriever.findByUserId(userId);
        userSaver.saveCommitmentMessage(user, request.commitmentMessage());

        return new CommitmentMessageResponse(user.getCommitmentMessage());
    }

}
