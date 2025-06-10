package com.ureca.ufit.entity;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ureca.ufit.entity.enums.Gender;
import com.ureca.ufit.entity.enums.Role;
import com.ureca.ufit.global.domain.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Size(max = 50)
	@NotNull
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotNull
	@Column(name = "password", nullable = false)
	private String password;

	@Positive
	@NotNull
	@Column(name = "age", nullable = false)
	private int age;

	@NotNull
	@Column(name = "family", nullable = false)
	private int family;

	@NotNull
	@Enumerated(STRING)
	@Column(name = "gender", nullable = false)
	private Gender gender;

	@NotNull
	@Enumerated(STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@NotNull
	@Column(name = "rate_plan_id", nullable = false)
	private String ratePlanId;

	@Builder(access = PRIVATE)
	private User(String email, String password, int age, int family, Gender gender, Role role, String ratePlanId) {
		this.email = email;
		this.password = password;
		this.age = age;
		this.family = family;
		this.gender = gender;
		this.role = role;
		this.ratePlanId = ratePlanId;
	}

	public static User of(String email, String password, int age, int family, Gender gender, Role role, String ratePlanId){
		return User.builder()
				.email(email)
				.password(password)
				.age(age)
				.family(family)
				.gender(gender)
				.role(role)
				.ratePlanId(ratePlanId)
				.build();
	}
}
