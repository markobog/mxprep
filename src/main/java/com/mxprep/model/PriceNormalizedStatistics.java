package com.mxprep.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PriceNormalizedStatistics {

    Company getCompany();

    LocalDate getDate();

    BigDecimal getClose();

    BigDecimal getOpen();

    BigDecimal getLow();

    BigDecimal getHigh();

    String getVolume();

    BigDecimal getNormalized();

}
