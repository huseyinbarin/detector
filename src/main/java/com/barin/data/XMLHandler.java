package com.barin.data;

import com.barin.domain.Company;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class XMLHandler extends DefaultHandler {
    private StringBuilder title;
    private StringBuilder text;
    private boolean isInTitle;
    private boolean isInText;
    private final List<Company> companies;
    private final Set<String> mentionedCompanies;

    public XMLHandler(List<Company> companies) {
        this.companies = companies;
        this.mentionedCompanies = new HashSet<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("title")) {
            title = new StringBuilder();
            isInTitle = true;
        } else if (qName.equalsIgnoreCase("text")) {
            text = new StringBuilder();
            isInText = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (isInTitle) {
            title.append(ch, start, length);
        } else if (isInText) {
            text.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("title")) {
            isInTitle = false;
        } else if (qName.equalsIgnoreCase("text")) {
            isInText = false;
            checkForMentionedCompanies(title.toString(), text.toString());
        }
    }

    void checkForMentionedCompanies(String title, String text) {
        for (Company company : companies) {
            if (title.contains(company.getName()) || text.contains(company.getName())) {
                this.mentionedCompanies.add(company.getName());
                continue;
            }

            if(!Objects.equals(company.getParenthesisName(), "")){
                if (title.contains(company.getParenthesisName()) || text.contains(company.getParenthesisName())) {
                    this.mentionedCompanies.add(company.getName());
                }
            }
        }
    }

    protected Set<String> getMentionedCompanies() {
        return this.mentionedCompanies;
    }

    protected void clearMentionedCompaniesSet() {
        this.mentionedCompanies.clear();
    }


}