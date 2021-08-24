package com.github.yeeun_yun97.main.model.dto.domain.lotto.pick;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class UpdateSaveRequest {
    private Long predict_id,user_id;
}
