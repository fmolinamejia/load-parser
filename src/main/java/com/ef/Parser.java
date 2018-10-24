package com.ef;

import com.ef.helpers.FileHelper;
import com.ef.helpers.UtilsHelper;
import com.ef.helpers.ValidatorHelper;
import com.ef.model.LogComment;
import com.ef.services.LogFileService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Parser {

    public static void main(String[] args) {

        List<String> inputRules = initializeRules();
        Map<Integer, String> expectedParameters = initializeExpectedParameters();

        Map<String, String> inputs = new HashMap<>();
        StandardServiceRegistry registry = null;
        SessionFactory factory = null;
        Session session = null;
        LogFileService logFileService = null;
        Query query = null;

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
        //UtilsHelper.printInputs(inputs);

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

        try {
            // Load log file
            logFileService.loadLogFileFromFile(filePath, expectedParameters);
        } catch (Exception e) {
            System.err.println("Error while loading file.");
            System.exit(0);
        }

        // Prepare dates
        

        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = "2017-01-01 00:00:00";
        String ed = "2017-01-01 00:00:59";
        try {
            System.out.println("StartDate: " + sdfStart.parse(sd));
            System.out.println("EndDate: " + sdfStart.parse(ed));
        } catch(Exception e) {

        }

        //query = session.createQuery("select log.ip from LogFile log where log.ip = :ip group by ip having total < 6 order by total");
        query = session.createQuery("SELECT log.ip, COUNT(*) FROM LogFile log WHERE log.date BETWEEN :startDate AND :endDate GROUP BY log.ip HAVING COUNT(*) < :threshold ORDER BY log.ip");
        //query = session.createQuery("SELECT log.ip, log.date FROM LogFile log WHERE log.date BETWEEN :startDate AND :endDate GROUP BY log.ip HAVING COUNT(*) < :threshold ORDER BY log.ip");
        //query = session.createQuery("SELECT log.ip, DATE_FORMAT(log.date, '%Y-%m-%d %H:%i:%s') FROM LogFile log WHERE log.date BETWEEN '2017-01-01 00:00:00' AND '2017-01-01 00:00:59' ORDER BY log.date");
        //query = session.createQuery("SELECT log.ip, DATE_FORMAT(log.date, '%Y-%m-%d %H:%i:%s') FROM LogFile log WHERE log.date BETWEEN :startDate AND :endDate ORDER BY log.date");
        //query = session.createQuery("SELECT log.ip, COUNT(*) from LogFile log GROUP BY log.ip HAVING COUNT(*) < 6 ORDER BY log.ip");
        try {
            query.setParameter("startDate", sdfStart.parse("2017-01-01 00:00:00"));
            query.setParameter("endDate", sdfEnd.parse("2017-01-01 00:00:59"));
        } catch (ParseException pe) {
            System.err.println("Error parsing dates");
            System.exit(0);
        }
//        query.setParameter("startDate", "2017-01-01 00:00:00");
//        query.setParameter("endDate", "2017-01-01 00:00:59");
        query.setParameter("threshold", new Long(6));

        List<?> logFiles = query.list();//session.createQuery("select log.ip from LogFile log", LogFile.class).list();

        List<LogComment> logComments = session.createQuery("from LogComment", LogComment.class).list();

        if (logFiles != null && !logFiles.isEmpty()) {
            System.out.println("Log Files entries: " + logFiles.size());
//            logFiles.forEach(System.out::println);
            for(int i = 0; i < logFiles.size(); i++) {
                Object[] row = (Object[]) logFiles.get(i);
                System.out.println("IP: " + row[0] + " Times: " + row[1]);
            }
        } else {
            System.out.println("Log Files entries: No entries found");
        }

        System.out.println("Log Files entries: " + (logFiles != null && !logFiles.isEmpty()?logFiles.size():"No entries found"));
        System.out.println("Log Comments entries: " + (logComments != null && !logComments.isEmpty()?logComments.size():"No entries found"));

        session.close();
    }

    public static List<String> initializeRules() {
        List<String> inputRules = new ArrayList<>();
        inputRules.add("^--startDate=([0-9]{4})-([0-1][0-9])-([0-3][0-9]).([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$");
        inputRules.add("^--duration=(hourly|daily)$");
        inputRules.add("^--threshold=\\d+$");

        return inputRules;
    }

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
