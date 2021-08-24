package com.github.yeeun_yun97.main.model.dto.domain.user.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RegisterRequest {
    private final String email;
    private final String password;
    private final String name;
}
