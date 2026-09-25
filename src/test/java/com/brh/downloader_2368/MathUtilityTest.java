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

    @Test
    void multiply() {
        MathUtility math = new MathUtility();

        assertAll(
                () ->  assertEquals(8, math.multiply(8, 1.0) ) ,
                () ->  assertEquals(-20, math.multiply(10, -2)),
                () ->  assertEquals( 0, math.multiply(1, 0))
        );
    }
}