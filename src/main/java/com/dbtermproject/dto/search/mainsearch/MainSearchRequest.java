package com.dbtermproject.dto.search.mainsearch;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainSearchRequest {
    String category;

    @Nullable
    String minPrice;

    @Nullable
    String maxPrice;

    @Nullable
    String keyword;
}
