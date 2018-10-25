package com.ef.helpers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorHelperTest {

    @Test
    public void containElementsTest() {

        String[] elements1 = null;
        String[] elements2 = new String[] {};
        String[] elements3 = {"Test"};

        assertFalse(ValidatorHelper.containElements(elements1), "Array should not contain elements");
        assertFalse(ValidatorHelper.containElements(elements2), "Array should not contain elements");
        assertTrue(ValidatorHelper.containElements(elements3), "Array should contain elements");
    }

    @Test
    public void validInputTest() {

        List<String> inputRules = new ArrayList<>();
        inputRules.add("^--startDate=([0-9]{4})-([0-1][0-9])-([0-3][0-9]).([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$");
        inputRules.add("^--duration=(hourly|daily)$");
        inputRules.add("^--threshold=\\d+$");

        String date1 = "--startDate=2017-01-01.13:01:01";
        String date2 = "--startDate=2017-01-01ZZ13:01:01";
        String duration1 = "--duration=hourly";
        String duration2 = "--duration=daily";
        String duration3 = "--duration=monthly";
        String threshold1 = "--threshold=0";
        String threshold2 = "--threshold=100";
        String threshold3 = "--threshold=-10";

        assertTrue(ValidatorHelper.validInput(date1, inputRules), date1 + " should be a valid input ");
        assertTrue(ValidatorHelper.validInput(duration1, inputRules), duration1 + " should be a valid input ");
        assertTrue(ValidatorHelper.validInput(duration2, inputRules), duration2 + " should be a valid input ");
        assertTrue(ValidatorHelper.validInput(threshold1, inputRules), threshold1 + " should be a valid input ");
        assertTrue(ValidatorHelper.validInput(threshold2, inputRules), threshold2 + " should be a valid input ");
        assertFalse(ValidatorHelper.validInput(date2, inputRules), date2 + " should not be a valid input ");
        assertFalse(ValidatorHelper.validInput(duration3, inputRules), duration3 + " should not be a valid input ");
        assertFalse(ValidatorHelper.validInput(threshold3, inputRules), threshold3 + " should not be a valid input ");
    }
}
