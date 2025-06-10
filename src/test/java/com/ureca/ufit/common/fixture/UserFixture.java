package com.ureca.ufit.common.fixture;

import com.ureca.ufit.entity.User;
import com.ureca.ufit.entity.enums.Gender;
import com.ureca.ufit.entity.enums.Role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFixture {

	public static User user(String email) {
		return User.of(email, "password", 10, 0, Gender.MAN, Role.USER, "aPlan");
	}
}
