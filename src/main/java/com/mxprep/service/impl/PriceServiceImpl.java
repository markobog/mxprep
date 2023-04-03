package com.mxprep.service.impl;

import com.mxprep.model.Price;
import com.mxprep.opencsv.PriceVerifier;
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

    private PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Price> csvToBean = new CsvToBeanBuilder<Price>(reader)
                    .withType(Price.class)
                    .withVerifier(new PriceVerifier())
                    .build();
            priceRepository.saveAll(csvToBean.parse());

        }

    }
}
