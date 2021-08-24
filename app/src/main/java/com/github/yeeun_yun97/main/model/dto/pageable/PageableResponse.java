package com.github.yeeun_yun97.main.model.dto.pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableResponse {
    protected Pageable pageable;
    protected int totalPages,totalElements,number,size,numberOfElements;
    protected Sort sort;
    protected boolean last,first,empty;
}
