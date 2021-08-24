package com.github.yeeun_yun97.main.model.dto.domain.lotto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReadSaveRequest {
    private int page_num;
    private Long user_id;
}
