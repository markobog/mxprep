package com.mxprep.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PriceStatistics {
    Company getCompany();

    LocalDate getMindate();

    LocalDate getMaxdate();

    BigDecimal getMinprice();

    BigDecimal getMaxprice();
}
