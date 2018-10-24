package com.ef.helpers;

import java.util.List;

/**
 * Helper class for validations
 * name: ValidatorHelper.java
 * date: Oct 24, 2018
 *
 * @author Fernando Molina
 * @version 1.0
 */
public class ValidatorHelper {

    /**
     * Method used to validate that an String array contains elements
     *
     * @param elements String array
     * @return Is valid or not
     */
    public static boolean containElements(String[] elements) {
        return elements != null && elements.length > 0;
    }

    /**
     * Method used to validate an input based on a list of rules
     *
     * @param input Input received
     * @param rules Rules expected
     * @return Is valid or not
     */
    public static boolean validInput(String input, List<String> rules) {

        boolean isValid = false;

        for(String rule : rules) {
            if (input.matches(rule)) {
                isValid = true;
                break;
            }
        }

        return isValid;
    }
}
