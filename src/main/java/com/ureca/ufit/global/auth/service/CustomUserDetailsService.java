package com.ureca.ufit.global.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ureca.ufit.domain.user.exception.UserErrorCode;
import com.ureca.ufit.domain.user.repository.UserRepository;
import com.ureca.ufit.entity.User;
import com.ureca.ufit.global.auth.details.CustomUserDetails;
import com.ureca.ufit.global.exception.RestApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email).orElseThrow(() ->
			new RestApiException(UserErrorCode.USER_NOT_FOUND)
		);

		return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getRole());
	}
}