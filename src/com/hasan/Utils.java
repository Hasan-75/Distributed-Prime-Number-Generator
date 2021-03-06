package com.hasan;

import java.awt.*;
import java.io.File;
import java.util.Comparator;
import java.util.Random;

import static java.lang.Math.sqrt;

public class Utils {
    public static final int COMMON_PORT = 6666;

    public static final String READY_MSG = "Ready";
    public static final String DONE_MSG = "Done";
    public static final String BYE_MSG = "bye";
    public static final String ASK_FOR_FILE_MSG = "file please";

    public static final String START_POINT_TAG = "start_point";
    public static final String END_POINT_TAG = "end_point";

    public static final String PARENT_DIR = "result"+File.separator;
    public static final String ON_PROCESSING_FILE_DIR = PARENT_DIR+"processing"+File.separator;
    public static final String INTERRUPTED_FILE_DIR = PARENT_DIR+"interrupted"+File.separator;
    public static final String CLIENT_PRIME_FILE_DIR = PARENT_DIR+"generated_client"+File.separator;
    public static final String SERVER_PRIME_FILE_DIR = PARENT_DIR+"generated_server"+File.separator;

    public static final String TITLE_SERVER_FRAME = "Server";
    public static final String TITLE_CLIENT_FRAME = "Client";

    public static final Color COLOR_TOP_PANEL_BG = Color.orange;

    private static Comparator<File> fileComparator;

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

    public static Comparator<File> getFileComparator(){
        if(fileComparator==null) {
            fileComparator = new Comparator<File>() {
                @Override
                public int compare(File file, File t1) {
                    long l1 = Long.parseLong(file.getName().split(" - ")[0]);
                    long l2 = Long.parseLong(t1.getName().split(" - ")[0]);
                    return (int) (l1 - l2);
                }
            };
        }

        return fileComparator;
    }
}
