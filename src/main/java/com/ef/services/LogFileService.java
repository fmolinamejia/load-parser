package com.ef.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogFileService {

    private Session session;
    private Query query;

    public LogFileService(Session session) {
        this.session = session;
    }

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

    public List<?> getLogFile(String date, String duration, String threshold) {
        return null;
    }
}
