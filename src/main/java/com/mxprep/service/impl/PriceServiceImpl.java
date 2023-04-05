package com.mxprep.service.impl;

import com.mxprep.model.Company;
import com.mxprep.model.Price;
import com.mxprep.model.PriceNormalizedStatistics;
import com.mxprep.model.PriceStatistics;
import com.mxprep.opencsv.PriceVerifier;
import com.mxprep.repository.CompanyRepository;
import com.mxprep.repository.PriceRepository;
import com.mxprep.service.IPriceService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PriceServiceImpl implements IPriceService {

    private final PriceRepository priceRepository;
    private final CompanyRepository companyRepository;

    public PriceServiceImpl(PriceRepository priceRepository, CompanyRepository companyRepository) {
        this.priceRepository = priceRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Price> save(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Optional<Company> company = findCompanyByName(file.getOriginalFilename());
            CsvToBeanBuilder<Price> priceCsvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            priceCsvToBeanBuilder.withType(Price.class);
            priceCsvToBeanBuilder.withVerifier(new PriceVerifier(company));
            CsvToBean<Price> csvToBean = priceCsvToBeanBuilder
                    .build();
            return priceRepository.saveAll(csvToBean.parse());
        }
    }

    @Override
    public List<PriceStatistics> getStatisticsByCompany() {
        return priceRepository.getPricesStatisticsByCompany();
    }

    @Override
    public List<PriceNormalizedStatistics> getPricesNormalizedDesc() {
        return priceRepository.getPricesNormalizedDesc();
    }

    @Override
    public List<PriceStatistics> getPricesStatisticsByCompanyName(String companyName) {
        return priceRepository.getPricesStatisticsByCompanyName(companyName);
    }

    @Override
    public List<PriceNormalizedStatistics> getPriceNormalizedMaxByDate(LocalDate date) {
        return priceRepository.getPriceNormalizedMaxForDate(date);
    }

    private Optional<Company> findCompanyByName(String fileName) {

        if (fileName == null) {
            return Optional.empty();
        }
        String fullName = null;
        Company company;
        if (fileName.contains("AAPL")) {
            fullName = "Apple";
        } else if (fileName.contains("MSFT")) {
            fullName = "Microsoft";
        } else if (fileName.contains("IBM")) {
            fullName = "IBM";
        } else if (fileName.contains("EPAM")) {
            fullName = "EPAM";
        }
        company = companyRepository.findByCompanyName(fullName);
        return Optional.of(company);
    }
}
