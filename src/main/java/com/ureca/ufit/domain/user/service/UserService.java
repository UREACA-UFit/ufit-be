package com.ureca.ufit.domain.user.service;

import com.ureca.ufit.domain.user.dto.request.RegisterRequest;
import com.ureca.ufit.domain.user.dto.response.RegisterResponse;
import com.ureca.ufit.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ureca.ufit.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	//DB 확인용 회원가입
	@Transactional
	public RegisterResponse register(RegisterRequest request) {
		String encodedPassword = passwordEncoder.encode(request.password());

		User user = User.of(request.email(),
				encodedPassword,
				request.age(),
				request.family(),
				request.gender(),
				request.role(),
				request.ratePlanId());
		userRepository.save(user);

		return new RegisterResponse(true);
	}


}
