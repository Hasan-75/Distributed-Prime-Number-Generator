package com.hasan;

import com.hasan.client.PrimeClientSocket;
import com.hasan.server.ServerHandler;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	    //initializing file directories
        File file = new File(Utils.PARENT_DIR);
        if(!file.exists())
            file.mkdir();

        Thread t1 = new Thread(() -> {
            try {
                ServerHandler.startServer(Utils.COMMON_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();
    }
}
