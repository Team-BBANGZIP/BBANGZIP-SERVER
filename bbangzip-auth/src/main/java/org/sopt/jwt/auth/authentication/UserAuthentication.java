package org.sopt.jwt.auth.authentication;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserAuthentication extends AbstractAuthenticationToken {

    private final Long userId;
    @Getter
    private final UserRole role;

    private UserAuthentication(Long userId, UserRole role, boolean authenticated) {
        super(List.of(new SimpleGrantedAuthority("ROLE_" + role.name())));
        this.userId = userId;
        this.role = role;
        super.setAuthenticated(authenticated);
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public static UserAuthentication create(Long userId, UserRole role) {
        return new UserAuthentication(userId, role, true);
    }
}