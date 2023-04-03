package com.mxprep.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "price", schema = "mxprep")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_price")
    private long id;

    @CsvBindByName
    @CsvDate(value = "MM/dd/yyyy")
//    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate date;

    @CsvBindByName
    private BigDecimal open;

    @CsvBindByName
    private BigDecimal high;

    @CsvBindByName
    private BigDecimal low;

    @CsvBindByName
    private BigDecimal close;

    @CsvBindByName
    private String volume;

    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    private Company company;

}
