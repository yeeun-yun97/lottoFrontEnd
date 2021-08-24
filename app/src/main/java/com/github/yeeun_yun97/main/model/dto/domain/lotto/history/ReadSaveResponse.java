package com.github.yeeun_yun97.main.model.dto.domain.lotto.history;

import com.github.yeeun_yun97.main.model.dto.pageable.PageableResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReadSaveResponse extends PageableResponse {
private List<ReadSaveContent> content;


}
