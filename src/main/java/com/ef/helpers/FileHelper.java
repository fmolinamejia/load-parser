package com.ef.helpers;

import javax.swing.*;

/**
 * Helper class for File operations
 * name: FileHelper.java
 * date: Oct 24, 2018
 *
 * @author Fernando Molina
 * @version 1.0
 */
public class FileHelper {

    static final String INITIAL_PATH = System.getProperty("user.dir");
    static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * Method used to select a file using a file chooser.
     *
     * @return Path file
     */
    public static String selectFile() {
        String file = "";
        JFileChooser fileChooser = new JFileChooser(INITIAL_PATH);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile().getAbsolutePath();
        }

        return file;
    }
}
