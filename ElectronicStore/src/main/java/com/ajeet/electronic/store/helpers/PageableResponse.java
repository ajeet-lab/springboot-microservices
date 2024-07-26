package com.ajeet.electronic.store.helpers;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {

    private List<T> content;
    private int pageSize;
    private int pageNumber;
    private long totalElements;
    private long totalPages;
    private boolean isLastPage;

}
