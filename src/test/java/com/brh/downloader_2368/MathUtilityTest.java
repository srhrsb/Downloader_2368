package com.brh.downloader_2368;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilityTest {

    @Test
    void addNumbers() {

        MathUtility math = new MathUtility();
        double result = math.addNumbers(7.1, 1.0);
        assertEquals(8.1, result);

    }
}