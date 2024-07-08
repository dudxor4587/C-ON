package com.dbtermproject.dto.search.ordersearch;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchRequest {
    @Nullable
    LocalDate startDate;

    @Nullable
    LocalDate endDate;
}
