package com.github.yeeun_yun97.main.model.dto.domain.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ReadUserInfoRequest {
    public Long user_id;
}
