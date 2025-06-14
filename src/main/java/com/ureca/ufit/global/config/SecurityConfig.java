package com.ureca.ufit.global.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ureca.ufit.entity.enums.Role;
import com.ureca.ufit.global.auth.filter.ExceptionHandlerFilter;
import com.ureca.ufit.global.auth.filter.JwtFilter;
import com.ureca.ufit.global.auth.filter.LoginFilter;
import com.ureca.ufit.global.auth.handler.CustomLoginSuccessHandler;
import com.ureca.ufit.global.auth.handler.CustomLogoutHandler;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] USER_AUTH_LIST = {
		// 인증이 필요한 API 패턴
		"/api/users/jwt/test"
	};

	private static final String[] ADMIN_AUTH_LIST = {
		// 관리자 인증이 필요한 API 패턴
	};

	private final JwtFilter jwtFilter;
	private final CustomLoginSuccessHandler loginSuccessHandler;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final CustomLogoutHandler customLogoutHandler;
	private final ExceptionHandlerFilter exceptionHandlerFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public LoginFilter loginFilter() throws Exception {
		LoginFilter loginFilter = new LoginFilter();
		loginFilter.setAuthenticationManager(authenticationManager());
		loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
		return loginFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http
			.addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class) // ← 예외 핸들러
			.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class) // ← 로그인 필터
			.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class); // ← JWT 필터

		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(USER_AUTH_LIST).authenticated()
				.requestMatchers(ADMIN_AUTH_LIST).hasRole(Role.ADMIN.name())
				.anyRequest().permitAll());

		http
			.logout(logoutConfig -> logoutConfig
				.logoutUrl("/api/auth/logout")
				.addLogoutHandler(customLogoutHandler)
				.logoutSuccessHandler((request, response, authentication) -> {
					response.setStatus(HttpServletResponse.SC_OK);
				})
			);

		return http.build();
	}

	// Spring Security cors Bean 등록
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(
			List.of(
				"http://localhost:3000", "https://u-fit.site"
			));
		configuration.setExposedHeaders(List.of("Authorization"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3000L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
