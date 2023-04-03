package com.mxprep.service.impl;

import com.mxprep.model.Company;
import com.mxprep.model.Price;
import com.mxprep.opencsv.PriceVerifier;
import com.mxprep.repository.CompanyRepository;
import com.mxprep.repository.PriceRepository;
import com.mxprep.service.PriceService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final CompanyRepository companyRepository;

    public PriceServiceImpl(PriceRepository priceRepository, CompanyRepository companyRepository) {
        this.priceRepository = priceRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Company company = findCompanyByName(file.getOriginalFilename());
            CsvToBeanBuilder<Price> priceCsvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            priceCsvToBeanBuilder.withType(Price.class);
            priceCsvToBeanBuilder.withVerifier(new PriceVerifier(company));
            CsvToBean<Price> csvToBean = priceCsvToBeanBuilder
                    .build();
            priceRepository.saveAll(csvToBean.parse());
        }
    }

    private Company findCompanyByName(String fileName) {

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
        return company;
    }
}
