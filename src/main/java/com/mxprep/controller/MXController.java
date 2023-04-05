package com.mxprep.controller;

import com.mxprep.model.PriceNormalizedStatistics;
import com.mxprep.model.PriceStatistics;
import com.mxprep.service.IPriceService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MXController {

    private final IPriceService priceService;
    private final Logger logger = LoggerFactory.getLogger(MXController.class);

    public MXController(IPriceService priceService) {
        this.priceService = priceService;
    }

    HttpStatus httpStatus;

    @PostMapping("/upload-csv-file")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        String logMessage;
        if (file.isEmpty() && !StringUtils.isNotEmpty(file.getOriginalFilename())) {
            logMessage = "No file provided. Please select a CSV file to upload.";
            logger.info(logMessage);
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            priceService.save(file);
            logMessage = "Uploaded the file successfully: " + file.getOriginalFilename() + " and inserted data to DB.";
            logger.info(logMessage);
            httpStatus = HttpStatus.OK;
        }
        return ResponseEntity.status(httpStatus).body(logMessage);
    }

    @GetMapping("/stocks/get-statistics-by-company")
    @Cacheable(value = "statisticsByCompany")
    public ResponseEntity<List<PriceStatistics>> getStatisticsByCompany() {
        List<PriceStatistics> priceStatistics = priceService.getStatisticsByCompany();
        logger.info("Fetched statistics data by company...");
        return ResponseEntity.ok(priceStatistics);
    }

    @GetMapping("/stocks/get-normalized-descending")
    @Cacheable(value = "normalizedDescending")
    public ResponseEntity<List<PriceNormalizedStatistics>> getNormalizedStocksDescending() {
        List<PriceNormalizedStatistics> priceNormalizedStatistics = priceService.getPricesNormalizedDesc();
        logger.info("Fetched descending normalized list of all stocks...");
        return ResponseEntity.ok(priceNormalizedStatistics);
    }

    @GetMapping("/stocks/get-statistics-by-company/{company-name}")
    @Cacheable(value = "statisticsByCompanyName")
    public ResponseEntity<List<PriceStatistics>> getStatisticsByCompanyName(@PathVariable(name = "company-name") String companyName) {
        List<PriceStatistics> statisticsByCompanyName = priceService.getPricesStatisticsByCompanyName(companyName);
        logger.info(companyName, "Fetched normalized list of stocks for company: {0}.");
        return ResponseEntity.ok(statisticsByCompanyName);
    }

    @GetMapping("/stocks/get-highest-normalized-by-date/{date}")
    @Cacheable(value = "normalizedForDate")
    public ResponseEntity<List<PriceNormalizedStatistics>> getPriceNormalizedMaxByDate(@PathVariable(name = "date") @DateTimeFormat(pattern = "MM-dd-yyyy") LocalDate date) {
        List<PriceNormalizedStatistics> priceNormalizedMaxByDate = priceService.getPriceNormalizedMaxByDate(date);
        String dateString = date.toString();
        logger.info(dateString, "Fetched highest normalized stock for date: {0}.");
        return ResponseEntity.ok(priceNormalizedMaxByDate);
    }

}