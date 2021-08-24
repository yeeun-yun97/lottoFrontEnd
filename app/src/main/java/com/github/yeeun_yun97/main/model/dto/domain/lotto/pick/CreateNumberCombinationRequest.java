package com.github.yeeun_yun97.main.model.dto.domain.lotto.pick;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateNumberCombinationRequest {
    private Long user_id;
    private int[] numbers;
}
