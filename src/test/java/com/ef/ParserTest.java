package com.ef;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserTest {

    @Test
    public void initializeRulesTest() {

        List<String> inputRules = new ArrayList<>();
        inputRules.add("^--startDate=([0-9]{4})-([0-1][0-9])-([0-3][0-9]).([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$");
        inputRules.add("^--duration=(hourly|daily)$");
        inputRules.add("^--threshold=\\d+$");

        assertTrue(inputRules.containsAll(Parser.initializeRules()), "Initialize rules does not contain all rules expected");
    }

    @Test
    public void initializeExpectedParametersTest() {

        Map<Integer, String> expectedParameters = new HashMap<>();
        expectedParameters.put(1, "date");
        expectedParameters.put(2, "ip");
        expectedParameters.put(3, "request");
        expectedParameters.put(4, "status");
        expectedParameters.put(5, "userAgent");

        Map<Integer, String> currentParameters = Parser.initializeExpectedParameters();

        expectedParameters.forEach((key, value) -> {
            assertTrue(currentParameters.containsKey(key), "Expected parameters does not contain all keys expected");
            assertTrue(currentParameters.get(key).equals(expectedParameters.get(key)), "Expected parameters does not contain expected value for key");
        });

    }
}
