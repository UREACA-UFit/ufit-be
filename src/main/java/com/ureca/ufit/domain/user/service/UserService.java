package com.ureca.ufit.domain.user.service;

import org.springframework.stereotype.Service;

import com.ureca.ufit.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
}
