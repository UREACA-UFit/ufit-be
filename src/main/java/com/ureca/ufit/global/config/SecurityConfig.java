package com.ureca.ufit.global.config;

import com.ureca.ufit.global.auth.filter.JwtFilter;
import com.ureca.ufit.global.auth.filter.LoginFilter;
import com.ureca.ufit.global.auth.handler.CustomLogoutHandler;
import com.ureca.ufit.global.auth.handler.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] AUTH_LIST = {
			"/api/auth/login",
	};

	private final JwtFilter jwtFilter;
	private final LoginSuccessHandler loginSuccessHandler;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final CustomLogoutHandler customLogoutHandler;

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
			// 클라이언트가 리프레시 토큰을 재발급 요청 때만 쿠키에서 사용하므로 csrf는 토큰 재발급 요청 때만 사용
			//.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.csrf(AbstractHttpConfigurer::disable) // swagger로 api 요청 확인 때만 사용
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(
				(session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		http
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(loginFilter(), JwtFilter.class)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(AUTH_LIST).authenticated()
				.anyRequest().permitAll()
			);

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
				"http://localhost:3000"
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
