package com.brh.downloader_2368;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilityTest {

    @Test
    void addNumbers() {

        MathUtility math = new MathUtility();

        // asserts ohne assertAll sind ungünstig
        // da nach dem ersten gescheiterten Assert
        //abgebrochen wird
        // assertEquals(8.1, math.addNumbers(7.1, 2.0) );
        // assertEquals(10, math.addNumbers(10, 1));

        assertAll(

                () ->  assertEquals(8.1, math.addNumbers(7.1, 1.0) ) ,
                () ->  assertEquals(10, math.addNumbers(10, 1)),
                () ->  assertTrue( math.addNumbers(10, 1)>0)
        );

    }
}