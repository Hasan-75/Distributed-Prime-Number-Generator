package com.hasan.client;

import com.hasan.client.PrimeClientSocket;
import com.hasan.client.gui.ClientUI;

import java.io.IOException;

public class MainClientUI {

    public static void main(String[] args) {
        ClientUI clientUI = new ClientUI();
        clientUI.createFrame();
    }

    public static void connectToServer(String ip, int port){
        try {
            PrimeClientSocket primeClientSocket = new PrimeClientSocket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
