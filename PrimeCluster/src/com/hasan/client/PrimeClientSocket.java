package com.hasan.client;

import com.hasan.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PrimeClientSocket extends Socket {
    private InetAddress serverIp, selfIp;
    private int serverPort;
    private DataInputStream dis;
    private DataOutputStream dos;

    public PrimeClientSocket(String host, int port) throws IOException {
        super(host, port);
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.dis = new DataInputStream(getInputStream());
        this.dos = new DataOutputStream(getOutputStream());
        this.selfIp = getInetAddress();
        sendSomething();
    }

    private void sendSomething() {
        String messages[] = {"Hi", "Hey there", "Hello server", "You on?", "Doing great", "Proud of you!"};
        new Thread(() -> {
            int convLength = Utils.getRandomNumber(3, 1);
            while (convLength-->0){
                try {
                    dos.writeUTF(messages[Utils.getRandomNumber(messages.length-1, 0)]);
                    Thread.sleep(Utils.getRandomNumber(3000, 500));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
