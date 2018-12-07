package com.shxy.datashared.utils;

import org.junit.Test;

import java.util.Random;

public class SKUtils {
    private static final Random r = new Random();
    private static final int SK_LENGTH = 20;


    public static String generateRandomSK() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SK_LENGTH; i++) {
            sb.append((char) (r.nextInt(26) + 97));
        }
        return sb.toString();
    }
    @Test
    public void test(){
        System.out.println(generateRandomSK());
    }
}
