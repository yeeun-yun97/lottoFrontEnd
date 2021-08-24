package com.github.yeeun_yun97.main.model.dto.domain.user.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequest {
    private final String email;
    private final String password;
}
