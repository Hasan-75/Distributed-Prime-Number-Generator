package com.hasan;

import com.hasan.client.PrimeClientSocket;
import com.hasan.server.ServerHandler;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //mockClientTest();
        Thread t1 = new Thread(() -> {
            try {
                ServerHandler.startServer(Utils.COMMON_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();



    }

    /*Creating Some mock clients to test*/
    public static void mockClientTest() {
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("==============================");
            System.out.println("Creating mock clients");
            System.out.println("==============================");
            System.out.println();
            int i = 3;
            while (i-->0){
                try {
                    PrimeClientSocket primeClientSocket = new PrimeClientSocket("localhost", Utils.COMMON_PORT);
                    Thread.sleep(Utils.getRandomNumber(5000, 1000));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }
}
