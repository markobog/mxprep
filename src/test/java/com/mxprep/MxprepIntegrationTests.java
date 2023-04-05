package com.mxprep;

import com.mxprep.model.Company;
import com.mxprep.model.PriceNormalizedStatistics;
import com.mxprep.model.PriceNormalizedStatisticsEntity;
import com.mxprep.model.PriceStatistics;
import com.mxprep.model.PriceStatisticsEntity;
import com.mxprep.service.IPriceService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MxprepIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IPriceService priceService;
    @Test
    public void testUploadCsvFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "IBM.csv", "text/csv", "03/31/2023,129.47,131.23,129.42,131.09,4524686".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload-csv-file").file(file)).andExpect(status().isOk()).andExpect(content().string("Uploaded the file successfully: IBM.csv and inserted data to DB."));
        verify(priceService).save(file);
    }

    @Test
    public void testGetStatisticsByCompany() throws Exception {
        List<PriceStatistics> expectedStatistics = new ArrayList<>();
        expectedStatistics.add(createPriceStatisticsEntity());
        when(priceService.getStatisticsByCompany()).thenReturn(expectedStatistics);
        mockMvc.perform(get("/stocks/get-statistics-by-company")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
        verify(priceService).getStatisticsByCompany();
    }

    @Test
    public void testGetNormalizedStocksDescending() throws Exception {
        List<PriceNormalizedStatistics> expectedNormalizedStats = new ArrayList<>();
        expectedNormalizedStats.add(createPriceNormalizedStatisticsEntity());
        when(priceService.getPricesNormalizedDesc()).thenReturn(expectedNormalizedStats);
        mockMvc.perform(get("/stocks/get-normalized-descending")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
        verify(priceService).getPricesNormalizedDesc();
    }

    @Test
    public void testGetStatisticsByCompanyName() throws Exception {
        String companyName = "IBM";
        List<PriceStatistics> expectedStatistics = new ArrayList<>();
        expectedStatistics.add(createPriceStatisticsEntity());
        when(priceService.getPricesStatisticsByCompanyName(companyName)).thenReturn(expectedStatistics);
        mockMvc.perform(get("/stocks/get-statistics-by-company/{company-name}", companyName)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
        verify(priceService).getPricesStatisticsByCompanyName(companyName);
    }

    @Test
    public void testGetPriceNormalizedMaxByDate() throws Exception {
        LocalDate date = LocalDate.of(2023, 3, 30);
        List<PriceNormalizedStatistics> expectedNormalizedStats = new ArrayList<>();
        expectedNormalizedStats.add(createPriceNormalizedStatisticsEntity());
        when(priceService.getPriceNormalizedMaxByDate(date)).thenReturn(expectedNormalizedStats);
        mockMvc.perform(get("/stocks/get-highest-normalized-by-date/{date}", date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")))).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
        verify(priceService).getPriceNormalizedMaxByDate(date);
    }

    private PriceStatisticsEntity createPriceStatisticsEntity() {
        LocalDate date = LocalDate.of(2023, 3, 30);
        Company company = new Company();
        company.setCompanyName("IBM");
        company.setId(1);
        PriceStatisticsEntity priceStatisticsEntity = new PriceStatisticsEntity();
        priceStatisticsEntity.setCompany(company);
        priceStatisticsEntity.setMaxdate(LocalDate.of(2023, 3, 30));
        priceStatisticsEntity.setMindate(LocalDate.of(2023, 3, 30));
        priceStatisticsEntity.setMaxprice(new BigDecimal(100));
        priceStatisticsEntity.setMinprice(new BigDecimal(80));
        return priceStatisticsEntity;
    }

    private PriceNormalizedStatisticsEntity createPriceNormalizedStatisticsEntity() {
        LocalDate date = LocalDate.of(2023, 3, 30);
        Company company = new Company();
        company.setCompanyName("IBM");
        company.setId(1);
        PriceNormalizedStatisticsEntity priceNormalizedStatisticsEntity = new PriceNormalizedStatisticsEntity();
        priceNormalizedStatisticsEntity.setCompany(company);
        priceNormalizedStatisticsEntity.setDate(LocalDate.of(2023, 3, 30));
        priceNormalizedStatisticsEntity.setHigh(new BigDecimal(100));
        priceNormalizedStatisticsEntity.setLow(new BigDecimal(80));
        priceNormalizedStatisticsEntity.setOpen(new BigDecimal(81));
        priceNormalizedStatisticsEntity.setClose(new BigDecimal(89));
        priceNormalizedStatisticsEntity.setNormalized(new BigDecimal("0.098765"));
        priceNormalizedStatisticsEntity.setVolume("123456");
        return priceNormalizedStatisticsEntity;
    }


}
