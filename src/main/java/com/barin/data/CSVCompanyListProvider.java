package com.barin.data;

import com.barin.domain.Company;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CSVCompanyListProvider implements CompanyListProvider {

    private final String csvFilePath;

    public CSVCompanyListProvider(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public List<Company> getCompanyNames() throws IOException, CsvException {
        InputStream inputStream;
        Reader reader;

        // Check if the CSV file path is an external path or a resource path

        if (csvFilePath.startsWith("classpath:")) {
            // Load the resource file from the classpath
            String resourcePath = csvFilePath.substring("classpath:".length());
            inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        } else {

            // Load the CSV file from the external file path
            File file = new File(csvFilePath);
            reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8);
        }

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();

        List<String[]> rows = csvReader.readAll();
        csvReader.close();

        return rows.stream()
                .filter(row -> row.length >= 2)
                .map(row -> {
                    System.out.println(row[1]);
                    String parenthesisName = extractParenthesisName(row[1]);
                    return new Company(row[0], row[1], parenthesisName);
                })
                .collect(Collectors.toList());
    }

    private String extractParenthesisName(String name) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
