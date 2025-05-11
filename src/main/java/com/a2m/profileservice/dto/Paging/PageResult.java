package com.a2m.profileservice.dto.Paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T, B> {
    List<T> data;
    B cursor;
    private boolean hasNext;
    private int totalCount;
}
