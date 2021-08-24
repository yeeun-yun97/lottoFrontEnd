package com.github.yeeun_yun97.main.model.dto.domain.user.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class LoginResponse {
    private Long user_id;
    private String token;
}
