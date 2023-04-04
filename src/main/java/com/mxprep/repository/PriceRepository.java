package com.mxprep.repository;

import com.mxprep.model.Price;
import com.mxprep.model.PriceNormalizedStatistics;
import com.mxprep.model.PriceStatistics;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

    @Query("SELECT p.company as company, MIN(p.date) as mindate, MAX(p.date) as maxdate, " +
            "MIN(p.low) as minprice, MAX(p.high) as maxprice FROM Price p GROUP BY company")
    List<PriceStatistics> getPricesStatisticsByCompany();

    @Query("SELECT p.company as company, p.close as close, p.open as open, p.low, p.high as high, " +
            "p.volume as volume, p.date as date, (p.high - p.low)/p.low as normalized " +
            "FROM Price p" +
            "order by normalized desc")
    List<PriceNormalizedStatistics> getPricesNormalizedDesc();

    @Query("SELECT p.company as company, MIN(p.date) as mindate, MAX(p.date) as maxdate, " +
            "MIN(p.low) as minprice, MAX(p.high) as maxprice FROM Price p INNER JOIN p.company c " +
            "WHERE c.companyName = :companyName GROUP BY company")
    List<PriceStatistics> getPricesStatisticsByCompanyName(@Param("companyName") String companyName);

}
