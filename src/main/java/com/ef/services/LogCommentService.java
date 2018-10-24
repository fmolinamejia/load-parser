package com.ef.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

/**
 * Service class for Log Comment operations
 * name: LogCommentService.java
 * date: Oct 24, 2018
 *
 * @author Fernando Molina
 * @version 1.0
 */
public class LogCommentService {

    private Session session;
    private Query query;

    public LogCommentService(Session session) {
        this.session = session;
    }

    /**
     * Method used to load log comments from a list of results
     *
     * @param date Date expected
     * @param duration Duration expected
     * @param threshold Threshold expected
     * @param startDate Start date defined
     * @param endDate End date defined
     * @param results List of results
     */
    public void loadLogCommentsFromResult(Date date, String duration, String threshold, String startDate, String endDate, List<String> results) {
        Transaction tx = session.beginTransaction();

        for(String result : results) {
            query = session.createNativeQuery("INSERT INTO log_comment (date, duration, threshold, comments) VALUES (:date, :duration, :threshold, :comment)");
            query.setParameter("date", date);
            query.setParameter("duration", duration);
            query.setParameter("threshold", new Long(threshold));
            query.setParameter("comment", String.format("%s is blocked due has %s or more request between %s and %s", result, threshold, startDate, endDate));

            query.executeUpdate();
        }

        tx.commit();
    }

}
