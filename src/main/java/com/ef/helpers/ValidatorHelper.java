package com.ef.helpers;

import java.util.List;

public class ValidatorHelper {

    public static boolean containElements(String[] elements) {
        return elements != null && elements.length > 0;
    }

    public static boolean validInput(String input, List<String> rules) {

        boolean isValid = false;

        for(String rule : rules) {
            if (input.matches(rule)) {
                isValid = true;
                break;
            }
        }
//
//        if (input.contains("--startDate=") || input.contains("--duration=") || input.contains("--threshold=")) {
//            return true;
//        }

        return isValid;
    }
}
