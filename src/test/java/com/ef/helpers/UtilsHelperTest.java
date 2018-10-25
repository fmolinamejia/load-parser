package com.ef.helpers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class UtilsHelperTest {

    @Test
    public void getInputTest() {

        Map<String, String> inputs = new HashMap<>();
        String input1 = "--startDate=2017-01-01";
        String input2 = "--duration=";
        String input3 = "--threshold";

        UtilsHelper.getInput(inputs, input1);

        assertTrue(!inputs.isEmpty(), "Inputs map should not be empty");
        assertTrue(inputs.containsKey("--startDate"), "Inputs map should contain --startDate");
        assertFalse(inputs.containsKey("--threshold"), "Inputs map not should contain --threshold");
        assertEquals(inputs.get("--startDate"), "2017-01-01", "Input map should have 2017-01-01 as value of --startDate");

        assertThrows(Exception.class, () -> UtilsHelper.getInput(inputs,input2), input2 + " should throws Exception");
        assertThrows(Exception.class, () -> UtilsHelper.getInput(inputs,input3), input3 + " should throws Exception");
    }

    @Test
    public void calculateDateTest() {

        String daily = "daily";
        String hourly = "hourly";
        String date1 = "2017-01-01.13:01:01";
        String startDateDaily1 = "2017-01-01 00:00:00";
        String endDateDaily1 = "2017-01-01 23:59:59";
        String startDateHourly1 = "2017-01-01 13:00:00";
        String endDateHourly1 = "2017-01-01 13:59:59";

        assertEquals(startDateDaily1, UtilsHelper.calculateDate("start", date1, daily),
                startDateDaily1 + " should be the start date for " + date1 + " on daily basis");
        assertEquals(endDateDaily1, UtilsHelper.calculateDate("end", date1, daily),
                endDateDaily1 + " should be the end date for " + date1 + " on daily basis");
        assertEquals(startDateHourly1, UtilsHelper.calculateDate("start", date1, hourly),
                startDateHourly1 + " should be the start date for " + date1 + " on hourly basis");
        assertEquals(endDateHourly1, UtilsHelper.calculateDate("end", date1, hourly),
                endDateHourly1 + " should be the end date for " + date1 + " on hourly basis");

    }

}
