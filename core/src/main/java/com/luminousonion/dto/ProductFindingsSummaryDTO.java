package com.luminousonion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFindingsSummaryDTO {
    private int critical;
    private int high;
    private int medium;
    private int low;
    private int informational;
    private int total;

    public ProductFindingsSummaryDTO(int critical, int high, int medium, int low, int info) {
        this.critical = critical;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.informational = info;
        this.total = (critical + high + medium + low + info);
    }

}
