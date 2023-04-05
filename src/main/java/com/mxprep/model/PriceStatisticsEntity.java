package com.mxprep.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PriceStatisticsEntity implements PriceStatistics {

    private Company company;

    private LocalDate mindate;

    private LocalDate maxdate;

    private BigDecimal minprice;

    private BigDecimal maxprice;

}
