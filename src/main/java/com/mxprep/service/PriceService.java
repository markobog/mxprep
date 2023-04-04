package com.mxprep.service;

import com.mxprep.model.PriceNormalizedStatistics;
import com.mxprep.model.PriceStatistics;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PriceService {

    void save(MultipartFile file) throws IOException;

    List<PriceStatistics> getStatisticsByCompany();

    List<PriceNormalizedStatistics> getPricesNormalizedDesc();

    List<PriceStatistics> getPricesStatisticsByCompanyName(String companyName);
}
