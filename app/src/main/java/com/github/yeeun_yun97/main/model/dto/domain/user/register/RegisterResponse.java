package com.github.yeeun_yun97.main.model.dto.domain.user.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
public class RegisterResponse {
    private String name;
    private String message;
}
