package com.ureca.ufit.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ureca.ufit.domain.user.repository.UserRepository;
import com.ureca.ufit.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	@DisplayName("")
	@Test
	void test() {
		// given

		// when

		// then

	}
}
