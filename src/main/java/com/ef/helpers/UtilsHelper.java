package com.ef.helpers;

import java.util.Map;

/**
 * Helper class for Common operations
 * name: UtilsHelper.java
 * date: Oct 24, 2018
 *
 * @author Fernando Molina
 * @version 1.0
 */
public class UtilsHelper {

    private static final String DURATION_DAILY = "daily";
    private static final String DATE_START = "start";

    /**
     * Method used to get an input from a Map
     *
     * @param inputs Map to store input
     * @param input Input received
     */
    public static void getInput(Map<String, String> inputs, String input) {
        String[] inputArr = input.split("=");
        String key = inputArr[0];
        String value = inputArr[1];

        inputs.put(key, value);
    }

    /**
     * Method used to print map values
     *
     * @param inputs Map values
     */
    public static void printInputs(Map<String, String> inputs) {
        // Get expected inputs
        inputs.forEach((key, value) -> System.out.printf("\n%s=%s", key, value));
    }

    /**
     * Method used to calculate the correct date.
     *
     * @param type Can be start|end
     * @param date Date received
     * @param duration Can be hourly|daily
     * @return Correct date
     */
    public static String calculateDate(String type, String date, String duration) {

        String[] timeArr = date.split(":");
        String[] dateArr = timeArr[0].split("\\.");
        StringBuilder expectedDate = new StringBuilder(dateArr[0]);
        expectedDate.append(" ");

        // Build daily dates
        if (duration.equalsIgnoreCase(DURATION_DAILY)) {
            if (type.equalsIgnoreCase(DATE_START)) {
                expectedDate.append("00:00:00");
            } else {
                expectedDate.append("23:59:59");
            }
        } else { // Build hourly dates
            expectedDate.append(dateArr[1]);
            if (type.equalsIgnoreCase(DATE_START)) {
                expectedDate.append(":00:00");
            } else {
                expectedDate.append(":59:59");
            }
        }

        return expectedDate.toString();
    }
}
