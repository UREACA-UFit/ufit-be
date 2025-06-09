package com.ureca.ufit.user.provider;

import com.ureca.ufit.domain.user.exception.UserErrorCode;
import com.ureca.ufit.global.auth.provider.LoginProvider;
import com.ureca.ufit.global.auth.service.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginProviderTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    CustomUserDetailsService userDetailsService;

    @InjectMocks
    LoginProvider loginProvider;

    @DisplayName("정상 로그인 시 Authentication 객체를 반환한다.")
    @Test
    void authenticateSuccess() {
        // given
        String email = "test@email.com";
        String rawPassword = "password123";
        String encodedPassword = "encoded_password";

        UserDetails userDetails = User.withUsername(email)
                .password(encodedPassword)
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(email, rawPassword);

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // when
        Authentication result = loginProvider.authenticate(token);

        // then
        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getPrincipal()).isEqualTo(userDetails);
    }

}
