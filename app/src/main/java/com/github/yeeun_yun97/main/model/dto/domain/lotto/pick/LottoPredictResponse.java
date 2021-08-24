package com.github.yeeun_yun97.main.model.dto.domain.lotto.pick;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class LottoPredictResponse {
    private Long predict_id, round_id;
    private List<Integer> numberList;
}
