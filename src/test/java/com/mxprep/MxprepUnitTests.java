package com.mxprep;

import com.mxprep.controller.MXController;
import com.mxprep.model.Price;
import com.mxprep.model.PriceNormalizedStatistics;
import com.mxprep.model.PriceStatistics;
import com.mxprep.service.IPriceService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MxprepUnitTests {

    private MXController controller;

    @Mock
    private IPriceService priceService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new MXController(priceService);
    }

    @Test
    void testUploadCsvFile_withEmptyFile_shouldReturnBadRequest() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "IBM.csv", "text/plain", new byte[0]);
        ResponseEntity<String> responseEntity = controller.uploadCsvFile(file);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUploadCsvFile_withNonEmptyFile_shouldReturnOk() throws IOException {
        byte[] content = Files.readAllBytes(Paths.get("src/test/resources/Download Data - STOCK_US_XNYS_IBM.csv"));
        MockMultipartFile file = new MockMultipartFile("file", "Download Data - STOCK_US_XNYS_IBM.csv", "text/plain", content);
        List<Price> testList = new ArrayList<Price>();
        when(priceService.save(file)).thenReturn(testList);
        ResponseEntity<String> responseEntity = controller.uploadCsvFile(file);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetStatisticsByCompany_shouldReturnOk() {
        List<PriceStatistics> expected = Collections.emptyList();
        when(priceService.getStatisticsByCompany()).thenReturn(expected);
        ResponseEntity<List<PriceStatistics>> responseEntity = controller.getStatisticsByCompany();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    void testGetNormalizedStocksDescending_shouldReturnOk() {
        List<PriceNormalizedStatistics> expected = Collections.emptyList();
        when(priceService.getPricesNormalizedDesc()).thenReturn(expected);
        ResponseEntity<List<PriceNormalizedStatistics>> responseEntity = controller.getNormalizedStocksDescending();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    void testGetStatisticsByCompanyName_shouldReturnOk() {
        String companyName = "test";
        List<PriceStatistics> expected = Collections.emptyList();
        when(priceService.getPricesStatisticsByCompanyName(companyName)).thenReturn(expected);
        ResponseEntity<List<PriceStatistics>> responseEntity = controller.getStatisticsByCompanyName(companyName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    void testGetPriceNormalizedMaxByDate_shouldReturnOk() {
        LocalDate date = LocalDate.now();
        List<PriceNormalizedStatistics> expected = Collections.emptyList();
        when(priceService.getPriceNormalizedMaxByDate(date)).thenReturn(expected);
        ResponseEntity<List<PriceNormalizedStatistics>> responseEntity = controller.getPriceNormalizedMaxByDate(date);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());
    }

}