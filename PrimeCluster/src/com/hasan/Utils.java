package com.hasan;

import java.util.Random;

import static java.lang.Math.sqrt;

public class Utils {
    public static final String READY_MSG = "Ready";
    public static final int COMMON_PORT = 6666;
    public static final String DONE_MSG = "Done";

    public static int getRandomNumber(int max, int min){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static boolean isPrime(long number)
    {
        long root = (long) sqrt(number);
        for(long i=2; i<=root; i++)
        {
            if(number%i == 0)
                return false;
        }
        return true;
    }
}
