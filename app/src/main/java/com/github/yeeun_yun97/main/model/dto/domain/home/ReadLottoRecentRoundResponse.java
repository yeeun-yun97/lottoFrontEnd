package com.github.yeeun_yun97.main.model.dto.domain.home;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReadLottoRecentRoundResponse {
    private int round_num;
    private String endDate;
    private List<Integer> hitNumbers;
    private int bonusNumber;

}
