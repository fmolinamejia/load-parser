package com.ef.helpers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileHelperTest {

    @Test
    public void selectFileTest() {

        String fileSelected = FileHelper.selectFile();

        // When there is a file selected and press ok
        assertTrue(!fileSelected.isEmpty(), "File selected should not be empty");

    }

    @Test
    public void cancelSelectFileTest() {

        String fileSelected = FileHelper.selectFile();

        // When press cancel
        assertTrue(fileSelected.isEmpty(), "File selected should be empty");

    }
}
