package com.mxprep.controller;

import com.mxprep.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}

