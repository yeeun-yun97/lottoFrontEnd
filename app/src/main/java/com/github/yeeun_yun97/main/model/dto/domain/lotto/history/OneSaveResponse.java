package com.github.yeeun_yun97.main.model.dto.domain.lotto.history;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OneSaveResponse{
    private List<Integer> numberList;
    private List<EScore> scoreList;
}
