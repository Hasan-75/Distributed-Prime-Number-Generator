package com.hasan;

import com.hasan.client.PrimeClientSocket;

import java.io.IOException;

public class MockClient1 {

    public static void main(String[] args) {
        try {
            PrimeClientSocket primeClientSocket = new PrimeClientSocket("localhost", Utils.COMMON_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
