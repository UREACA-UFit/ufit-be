package com.ureca.ufit.domain.user.dto.response;

import static lombok.AccessLevel.PRIVATE;

import com.ureca.ufit.entity.enums.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
        String email,
        Role role
) {

    @Builder(access = PRIVATE)
    public LoginResponse(String email, Role role){
        this.email = email;
        this.role = role;
    }

    public static LoginResponse of(String email, Role role){
        return LoginResponse.builder()
                .email(email)
                .role(role)
                .build();
    }
}
