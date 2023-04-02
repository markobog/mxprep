package com.mxprep.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "price", schema = "mxprep")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_price")
    private long id;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate date;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private Integer volume;

    @ManyToOne
    @JoinColumn(name="id_company", nullable=false)
    private Company company;

}
