package com.barin.data;

import com.opencsv.exceptions.CsvException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanyMentionService {

    private XMLHandler xmlHandler;
    private final AtomicInteger processedCount = new AtomicInteger(0);
    private int totalFiles;
    private final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();


    public CompanyMentionService(CompanyListProvider companyListProvider) throws IOException, CsvException {
        this.xmlHandler = new XMLHandler(companyListProvider.getCompanyNames());
    }


    public Set<String> getMentionedCompanies() {
        return this.xmlHandler.getMentionedCompanies();
    }

    public void setXmlHandler(XMLHandler xmlHandler) {
        this.xmlHandler = xmlHandler;
    }

    public void reportTheResults(){
        this.getMentionedCompanies().forEach(System.out::println);
    }

    public void processXMLFiles(String xmlFolderPath) throws InterruptedException {

        File dataFolder;
        File[] files;

        // Check if the XML folder path is an external path or a resource path
        if (xmlFolderPath.startsWith("classpath:")) {
            String resourcePath = xmlFolderPath.substring("classpath:".length());
            dataFolder = new File(getClass().getClassLoader().getResource(resourcePath).getFile());

        } else {
            // Load the XML folder from the external folder path
            dataFolder = new File(xmlFolderPath);
        }

        files = dataFolder.listFiles();


        if (files != null) {
            totalFiles = files.length;
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    executorService.execute(() -> processXMLFile(file));
                }
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
    }


    private void processXMLFile(File xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(xmlFile, xmlHandler);
            incrementProcessedCount();
        } catch (SAXParseException e) {
            System.err.println("Error parsing XML file: " + xmlFile);
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred while parsing XML file: " + xmlFile);
            e.printStackTrace();
        }
    }

    private void incrementProcessedCount() {
        System.out.println("Thread name:" + Thread.currentThread().getName());
        System.out.println("Processed: " + processedCount.getAndIncrement() + " / " + totalFiles);
    }


}

