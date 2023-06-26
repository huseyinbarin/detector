package com.barin;

import com.barin.data.CSVCompanyListProvider;
import com.barin.data.CompanyListProvider;
import com.barin.data.CompanyMentionService;

import java.util.Objects;
import java.util.Set;

public class CompanyMentionFinder {

    private final String csvFilePath;
    private final String xmlFolderPath;


    public CompanyMentionFinder(String csvFilePath, String xmlFolderPath) {
        this.csvFilePath = csvFilePath;
        this.xmlFolderPath = xmlFolderPath;
    }


    public static void main(String[] args) {

        if (args == null) throw new IllegalArgumentException("App is run by getting arguments from command line " +
                " please refer the manual $java jar detector-1.0.jar [--help|-h] ");

        if (args.length == 1){
            if(Objects.equals(args[0], "--help") || Objects.equals(args[0], "-h")) {
                printHelp();
            }else{
                printError();
            }
        }


        if (args.length > 1) {
            String csvFilePath = args[0];
            String xmlFolderPath = args[1];

            CompanyMentionFinder mentionFinder = new CompanyMentionFinder(csvFilePath, xmlFolderPath);
            mentionFinder.run();
       }

    }

    private static void printError() {
        System.err.println("undefined option please read manuel\nusage: java jar logparser-1.0.jar --help  or -h");
        System.exit(1);
    }

    public void run() {
        try {

            System.out.println("csvFilePath:" + csvFilePath);
            CompanyListProvider companyListProvider = new CSVCompanyListProvider(csvFilePath);
            CompanyMentionService companyMentionService = new CompanyMentionService(companyListProvider);

            long startTime = System.currentTimeMillis();
            companyMentionService.processXMLFiles(xmlFolderPath);

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            Set<String> mentionedCompanies = companyMentionService.getMentionedCompanies();
            companyMentionService.reportTheResults();
            System.out.println("Total execution time: " + executionTime + " ms");
            System.out.println("#Mentioned Companies: " + mentionedCompanies.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void printHelp() {
        String help = """
                Detector is simply detecting the companies mentioned in the XML files located according to the path given.
                You need to provide the [PATH] of the CSV file and [PATH] of the XML files located
                Options

                —help, -h
                 Show the help menu related to usage of app.

                Example Usage:
                
                $ java -jar detector-1.0.jar PATH_CSV_FILE PATH_XML_FILE(s) 

                """;

        System.out.println(help);
    }
}

