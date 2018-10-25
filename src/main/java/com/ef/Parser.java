package com.ef;

import com.ef.helpers.FileHelper;
import com.ef.helpers.UtilsHelper;
import com.ef.helpers.ValidatorHelper;
import com.ef.services.LogCommentService;
import com.ef.services.LogFileService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Main class that execute load and parser for a log file
 * name: Parser.java
 * date: Oct 24, 2018
 *
 * @author Fernando Molina
 * @version 1.0
 */
public class Parser {

    public static void main(String[] args) {

        List<String> inputRules = initializeRules();
        Map<Integer, String> expectedParameters = initializeExpectedParameters();

        Map<String, String> inputs = new HashMap<>();
        StandardServiceRegistry registry = null;
        SessionFactory factory = null;
        Session session = null;
        LogFileService logFileService = null;
        LogCommentService logCommentService = null;

        // Read command line parameters
        if (!ValidatorHelper.containElements(args)) {
            System.out.println("No parameters as part of the command line");
            System.exit(0);
        } else {

            // Valid expected inputs
            for(String input : args) {

                if (!ValidatorHelper.validInput(input, inputRules)) {
                    System.out.printf("\n %s is not an expected input ", input);
                    System.out.println("Expected inputs are: [--startDate|--duration|--threshold]");
                    System.exit(0);
                }

                // Get expected input
                UtilsHelper.getInput(inputs, input);

            }
        }

        // Print expected inputs
        UtilsHelper.printInputs(inputs);

        // Select valid log file
        String filePath = FileHelper.selectFile();

        // Build registry and factory
        registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        factory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        session = factory.openSession();
        logFileService = new LogFileService(session);
        logCommentService = new LogCommentService(session);

        try {
            // Load log file
            logFileService.loadLogFileFromFile(filePath, expectedParameters);
        } catch (Exception e) {
            System.err.println("Error while loading file.");
            System.exit(0);
        }

        // Prepare dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = UtilsHelper.calculateDate("start", inputs.get("--startDate"), inputs.get("--duration"));
        String ed = UtilsHelper.calculateDate("end", inputs.get("--startDate"), inputs.get("--duration"));
        List<String> logIps = null;

        try {
            // Search for log file records using entered criteria
            logIps = logFileService.getLogFileIps(sdf.parse(sd), sdf.parse(ed), inputs.get("--threshold"));
            // Comment due is used for test purposes
            //logIps = logFileService.getLogFileIps(sdf.parse("2017-01-01 00:00:00"), sdf.parse("2017-01-01 00:00:59"), "0");
        } catch(Exception e) {
            System.err.println("Error parsing startDate/endDate while getting IPs please verify.");
            System.exit(0);
        }

        try {
            String requestedDate = inputs.get("--startDate").replace(".", " ");
            // Process comments based on results
            logCommentService.loadLogCommentsFromResult(sdf.parse(requestedDate), inputs.get("--duration"),
            inputs.get("--threshold"), sd, ed, logIps);
        } catch (Exception e) {
            System.err.println("Error parsing startDate while inserting Comment please verify.");
            System.exit(0);
        }

        session.close();
        System.exit(0);
    }

    /**
     * Method used to initialize rules to validate each input
     *
     * @return List of rules
     */
    public static List<String> initializeRules() {
        List<String> inputRules = new ArrayList<>();
        inputRules.add("^--startDate=([0-9]{4})-([0-1][0-9])-([0-3][0-9]).([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$");
        inputRules.add("^--duration=(hourly|daily)$");
        inputRules.add("^--threshold=\\d+$");

        return inputRules;
    }

    /**
     * Method used to initialize expected parameters for log file
     *
     * @return Map of expected parameters
     */
    public static Map<Integer, String> initializeExpectedParameters() {
        Map<Integer, String> expectedParameters = new HashMap<>();
        expectedParameters.put(1, "date");
        expectedParameters.put(2, "ip");
        expectedParameters.put(3, "request");
        expectedParameters.put(4, "status");
        expectedParameters.put(5, "userAgent");

        return expectedParameters;
    }

}
