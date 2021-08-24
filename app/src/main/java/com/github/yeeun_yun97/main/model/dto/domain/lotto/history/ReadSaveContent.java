package com.github.yeeun_yun97.main.model.dto.domain.lotto.history;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReadSaveContent{
    private int round_num, year;
    private List<OneSaveResponse> content;

    public String getTitleText(){
        return this.year+"년 "+this.round_num+"회";
    }
}
