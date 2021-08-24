package com.github.yeeun_yun97.main.model.dto.domain.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReadUserInfoResponse {
    private String name;
}
