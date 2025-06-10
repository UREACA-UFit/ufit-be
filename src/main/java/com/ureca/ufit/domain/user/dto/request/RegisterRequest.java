package com.ureca.ufit.domain.user.dto.request;

import com.ureca.ufit.entity.enums.Gender;
import com.ureca.ufit.entity.enums.Role;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @Email(message = "이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 15, message = "비밀번호는 8~15자 사이여야 합니다.")
        @Pattern(
                regexp = "^(?=(?:.*[!@#$%^&*(),.?\":{}|<>\\[\\]\\/\\\\~`_+=\\-]){3,})(?=.*[a-z]).{6,}$",
                message = "비밀번호는 특수문자 3개 이상과 영문 소문자 1자 이상을 포함해야 합니다."
        )
        String password,

        @Min(value = 1, message = "나이는 1세 이상이어야 합니다.")
        int age,

        @Min(value = 1, message = "가구원 수는 최소 1명 이상이어야 합니다.")
        int family,

        @NotNull(message = "성별을 선택해주세요.")
        Gender gender,

        @NotNull(message = "권한을 설정해주세요.")
        Role role,

        @NotBlank(message = "요금제 ID는 필수입니다.")
        String ratePlanId
) {

}