package com.ureca.ufit.domain.user.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ureca.ufit.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
}
