package com.ef.helpers;

import javax.swing.*;

public class FileHelper {

    static final String INITIAL_PATH = System.getProperty("user.dir");
    static final String FILE_SEPARATOR = System.getProperty("file.separator");

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
