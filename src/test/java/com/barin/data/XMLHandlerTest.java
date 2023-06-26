package com.barin.data;

import com.barin.domain.Company;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XMLHandlerTest {
    @Test
    public void testCheckForMentionedCompanies() {
        List<Company> companies = Arrays.asList(
                new Company("1", "Company A", ""),
                new Company("2", "Company B", "Company B"),
                new Company("3", "Company C", "")
        );

        XMLHandler xmlHandler = new XMLHandler(companies);

        // Test case 1: Mentioned companies in both title and text
        String title = "Company A is mentioned";
        String text = "This is some text containing Company B";

        xmlHandler.checkForMentionedCompanies(title, text);

        Set<String> mentionedCompanies = xmlHandler.getMentionedCompanies();
        // Verify the mentioned companies
        assertEquals(2, mentionedCompanies.size());
        assertTrue(mentionedCompanies.contains("Company A"));
        assertTrue(mentionedCompanies.contains("Company B"));

        // Test case 2: Mentioned company only in the title
        title = "Company C";
        text = "This is some other text";

        xmlHandler.clearMentionedCompaniesSet();
        xmlHandler.checkForMentionedCompanies(title, text);

        // Verify the mentioned companies
        assertEquals(1, xmlHandler.getMentionedCompanies().size());
        assertTrue(xmlHandler.getMentionedCompanies().contains("Company C"));


        // Test case 3: Mentioned company only in the text
        title = "This is the title";
        text = "Some text mentioning Company A";
        xmlHandler.clearMentionedCompaniesSet();
        xmlHandler.checkForMentionedCompanies(title, text);

    }
}

