package com.hasan;

import java.util.Random;

public class Utils {
    public static int COMMON_PORT = 6666;

    public static int getRandomNumber(int max, int min){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
