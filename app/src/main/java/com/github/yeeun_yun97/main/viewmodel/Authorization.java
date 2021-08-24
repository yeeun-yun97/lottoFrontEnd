package com.github.yeeun_yun97.main.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Authorization {
    private String token;
    private Long user_id;
}
