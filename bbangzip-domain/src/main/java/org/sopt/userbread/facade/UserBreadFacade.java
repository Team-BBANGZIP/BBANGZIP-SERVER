package org.sopt.userbread.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.bread.domain.BreadEntity;
import org.sopt.bread.exception.BreadCoreErrorCode;
import org.sopt.bread.exception.BreadNotFoundException;
import org.sopt.bread.facade.BreadFacade;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.facade.UserFacade;
import org.sopt.userbread.domain.UserBreadEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBreadFacade {

    private final UserBreadRetriever userBreadRetriever;
    private final UserBreadSaver userBreadSaver;   // ✅ 저장 담당
    private final BreadFacade breadFacade;
    private final UserFacade userFacade;           // ✅ user 엔티티 조회

    @Transactional
    public void unlockSaltBreadOnSignUp(final long userId) {
        UserEntity user = userFacade.getUserEntityById(userId);

        BreadEntity saltBread = breadFacade.findByName("소금빵")
                .orElseThrow(() -> new BreadNotFoundException(BreadCoreErrorCode.BREAD_NOT_FOUND));

        if (userBreadRetriever.existsByUserIdAndBreadId(user.getId(), saltBread.getId())) {
            return;
        }

        UserBreadEntity link = UserBreadEntity.create(user, saltBread, true);
        userBreadSaver.save(link);
    }

    @Transactional(readOnly = true)
    public List<UserBreadEntity> findAllByUserId(Long userId) {
        return userBreadRetriever.findAllByUserId(userId);
    }
}