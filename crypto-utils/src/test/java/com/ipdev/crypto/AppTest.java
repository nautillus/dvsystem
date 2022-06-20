package com.ipdev.crypto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        byte b = (byte) 129;
        int i = Byte.toUnsignedInt(b);
        System.out.println("int unsigned: " + i);
        System.out.println("byte b: " + b);
        assertTrue( true );
    }
}
