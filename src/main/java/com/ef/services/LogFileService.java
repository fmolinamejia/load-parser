package com.ef.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Service class for Log File operations
 * name: LogFileService.java
 * date: Oct 24, 2018
 *
 * @author Fernando Molina
 * @version 1.0
 */
public class LogFileService {

    private Session session;
    private Query query;

    public LogFileService(Session session) {
        this.session = session;
    }

    /**
     * Method used to load a log file
     *
     * @param filePath Path of log file
     * @param expectedParameters Parameters from the log file
     * @throws Exception FileNotFound exception
     */
    public void loadLogFileFromFile(String filePath, Map<Integer, String> expectedParameters) throws Exception {

        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        Transaction tx = session.beginTransaction();

        while (sc.hasNextLine()) {
            String logLine = sc.nextLine();
            String[] logLineArr = logLine.split("\\|");
            int pos = 1;
            query = session.createNativeQuery("INSERT INTO log_file (date, ip, request, status, user_agent) VALUES (:date, :ip, :request, :status, :userAgent)");
            for(String logValue : logLineArr) {
                query.setParameter(expectedParameters.get(pos++), logValue);
            }
            query.executeUpdate();
        }

        tx.commit();
        sc.close();
    }

    /**
     * Method used to get a list of log ips based on expected criteria
     *
     * @param startDate Start date defined
     * @param endDate End date defined
     * @param threshold Threshold expected
     * @return IPs list
     */
    public List<String> getLogFileIps(Date startDate, Date endDate, String threshold) {

        query = session.createQuery("SELECT log.ip FROM LogFile log WHERE log.date BETWEEN :startDate AND :endDate GROUP BY log.ip HAVING COUNT(*) >= :threshold ORDER BY log.ip");

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("threshold", new Long(threshold));

        return query.list();

    }
}
