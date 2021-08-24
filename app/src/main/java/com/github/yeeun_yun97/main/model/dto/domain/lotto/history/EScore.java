package com.github.yeeun_yun97.main.model.dto.domain.lotto.history;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EScore {// hit: 당첨번호가 맞음, bonus_hit: 보너스 번호가 맞음, false: 아무것도 못 맞춤
    FALSE(0), HIT(10), BONUS_HIT(5);
    private int score;
}