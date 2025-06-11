package com.ureca.ufit.global.auth.provider;

import com.ureca.ufit.domain.user.exception.UserErrorCode;
import com.ureca.ufit.global.auth.service.CustomUserDetailsService;
import com.ureca.ufit.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String email = authentication.getName();

        // 인증 객체의 principal을 정의하기 위한 유저 정보
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 비밀번호 검증
        String password = authentication.getCredentials().toString();
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new RestApiException(UserErrorCode.USER_PASSWORD_MISMATCH);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
