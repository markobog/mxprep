package com.mxprep.opencsv;

import com.mxprep.model.Company;
import com.mxprep.model.Price;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.exceptions.CsvConstraintViolationException;

public class PriceVerifier implements BeanVerifier<Price> {
    @Override
    public boolean verifyBean(Price price) throws CsvConstraintViolationException {
        Company c = new Company();
        c.setId(4);
        c.setCompanyName("Apple");
        price.setCompany(c);
        return true;
    }
}
