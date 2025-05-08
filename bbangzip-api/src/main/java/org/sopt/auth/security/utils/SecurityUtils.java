package org.sopt.auth.security.utils;

import org.sopt.auth.exception.AuthConstant;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.auth.exception.UnAuthorizedException;

// Null check 대행할 유틸 클래스
public class SecurityUtils {

    public static Object checkPrincipal(final Object principal) {
        if (AuthConstant.ANONYMOUS_USER.equals(principal)) {
            throw new UnAuthorizedException(AuthErrorCode.UNAUTHORIZED);
        }
        return principal;
    }
}