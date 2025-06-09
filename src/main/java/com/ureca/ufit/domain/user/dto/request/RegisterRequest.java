package com.ureca.ufit.domain.user.dto.request;

import com.ureca.ufit.entity.enums.Gender;
import com.ureca.ufit.entity.enums.Role;

public record RegisterRequest(
        String email,
        String password,
        int age,
        int family,
        Gender gender,
        Role role,
        String ratePlanId
) {

}
