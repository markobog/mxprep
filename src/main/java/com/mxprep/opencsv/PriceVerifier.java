package com.mxprep.opencsv;

import com.mxprep.model.Company;
import com.mxprep.model.Price;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.exceptions.CsvConstraintViolationException;

public class PriceVerifier implements BeanVerifier<Price> {

    public PriceVerifier(Company company) {
        this.company = company;
    }

    private Company company;
    @Override
    public boolean verifyBean(Price price) throws CsvConstraintViolationException {
        price.setCompany(company);
        return true;
    }
}
