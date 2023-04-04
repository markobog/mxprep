package com.mxprep.controller;

import com.mxprep.model.Price;
import com.mxprep.model.PriceNormalizedStatistics;
import com.mxprep.model.PriceStatistics;
import com.mxprep.service.PriceService;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final PriceService priceService;
    private final Logger logger = LoggerFactory.getLogger(MXController.class);

    public MXController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/upload-csv-file")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        String logMessage;
        HttpStatus httpStatus;
        if (file.isEmpty()) {
            logMessage = "Please select a CSV file to upload";
            logger.info(logMessage);
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            try {
                priceService.save(file);
                logMessage = "Uploaded the file successfully: " + file.getOriginalFilename() + "and inserted data to DB";
                logger.info(logMessage);
                httpStatus = HttpStatus.OK;
            } catch (Exception e) {
                logMessage = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
                logger.info(logMessage);
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        }
        return ResponseEntity.status(httpStatus).body(logMessage);
    }

    @GetMapping("/stocks/get-statistics-by-company")
    public ResponseEntity<List<PriceStatistics>> getStatisticsByCompany() {
        return ResponseEntity.ok(priceService.getStatisticsByCompany());
    }

    @GetMapping("/stocks/get-normalized-descending")
    public ResponseEntity<List<PriceNormalizedStatistics>> getNormalizedStocksDescending() {
        return ResponseEntity.ok(priceService.getPricesNormalizedDesc());
    }

    @GetMapping("/stocks/get-statistics-by-company/{company-name}")
    public ResponseEntity<List<PriceStatistics>> getStatisticsByCompanyName(@PathVariable(name = "company-name") String companyName) {
        return ResponseEntity.ok(priceService.getPricesStatisticsByCompanyName(companyName));
    }

    @GetMapping("/stocks/get-highest-normalized-for-date/{date}")
    public ResponseEntity<List<Price>> getStatisticsByCompany(@PathVariable(name = "date") @DateTimeFormat(pattern = "MMddyyyy") LocalDate date) {
        List<Price> priceList = null;
        return ResponseEntity.ok(priceList);
    }


}

