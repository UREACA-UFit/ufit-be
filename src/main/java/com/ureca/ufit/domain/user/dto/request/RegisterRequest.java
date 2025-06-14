package com.ureca.ufit.domain.user.dto.request;

import com.ureca.ufit.entity.enums.Gender;
import com.ureca.ufit.entity.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
	@Email(message = "이메일 형식이 아닙니다.")
	@NotBlank(message = "이메일은 필수입니다.")
	@Schema(description = "이메일", example = "ufit@naver.com")
	String email,

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 8, max = 15, message = "비밀번호는 8~15자 사이여야 합니다.")
	@Pattern(
		regexp = "^(?=(?:.*[!@#$%^&*(),.?\":{}|<>\\[\\]\\/\\\\~`_+=\\-]){3,})(?=.*[a-z]).{6,}$",
		message = "비밀번호는 특수문자 3개 이상과 영문 소문자 1자 이상을 포함해야 합니다."
	)
	@Schema(description = "비밀번호", example = "Ufit123!@#")
	String password,

	@Min(value = 1, message = "나이는 1세 이상이어야 합니다.")
	@Schema(description = "나이", example = "20")
	int age,

	@Min(value = 1, message = "가구원 수는 최소 1명 이상이어야 합니다.")
	@Schema(description = "결합 가구원 수", example = "4")
	int family,

	@NotNull(message = "성별을 선택해주세요.")
	@Schema(description = "성별", example = "MAN")
	Gender gender,

	@NotNull(message = "권한을 설정해주세요.")
	@Schema(description = "권한", example = "USER")
	Role role,

	@NotBlank(message = "요금제 ID는 필수입니다.")
	@Schema(description = "요금제 ID", example = "665f3af6d6795e4f5e8e5a12")
	String ratePlanId
) {

}