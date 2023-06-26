package com.barin.data;

import com.barin.domain.Company;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface CompanyListProvider {
    List<Company> getCompanyNames() throws IOException, CsvException;
}
