package com.barin.data;

import com.opencsv.exceptions.CsvException;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CompanyMentionServiceTest {
    private static final String CSV_FILE_PATH = "classpath:test_company_list.csv";
    private static final String XML_FOLDER_PATH = "classpath:data";

    private CompanyMentionService companyMentionService;

    @BeforeEach
    public void setup() throws IOException, CsvException {
        CompanyListProvider companyListProvider = new CSVCompanyListProvider(CSV_FILE_PATH);
        companyMentionService = new CompanyMentionService(companyListProvider);
    }

//    @Test
//    public void testProcessXMLFiles() throws InterruptedException {
//
//
//        companyMentionService.processXMLFiles(XML_FOLDER_PATH);
//
//        Set<String> mentionedCompanies = companyMentionService.getMentionedCompanies();
//
//
//        System.out.println("mentioned comps:" + mentionedCompanies);
//        //assertEquals(2, mentionedCompanies.size());
//
//        Set<String> expectedMentionedCompanies = new HashSet<>();
//
//        expectedMentionedCompanies.add("TEST_COMPANY");
//        expectedMentionedCompanies.add("Apple Inc (Apple)");
//
//
//        //assertEquals(expectedMentionedCompanies, mentionedCompanies);
//
//
//    }

    @Test
    void reportTheResults_ShouldPrintMentionedCompanies() throws IOException, CsvException {


        // Create a mock PrintStream
        PrintStream mockPrintStream = mock(PrintStream.class);
        System.setOut(mockPrintStream);

        // Create a mock CSVCompanyListProvider
        CSVCompanyListProvider csvCompanyListProvider = mock(CSVCompanyListProvider.class);
        XMLHandler xmlHandler = mock(XMLHandler.class);

        // Set up the mentioned companies
        Set<String> mentionedCompanies = new HashSet<>(List.of(new String( "Company A")));

        when(xmlHandler.getMentionedCompanies()).thenReturn(mentionedCompanies);


        // Create the CompanyMentionService instance with the mock XMLHandler
        CompanyMentionService companyMentionService = new CompanyMentionService(csvCompanyListProvider);

        companyMentionService.setXmlHandler(xmlHandler);
        // Call the reportTheResults() method
        companyMentionService.reportTheResults();

        // Verify that System.out.println() was called for each mentioned company
        mentionedCompanies.forEach(companyName -> verify(mockPrintStream).println(companyName));

        // Reset System.out
        System.setOut(System.out);
    }
}
