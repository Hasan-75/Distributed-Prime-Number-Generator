package com.hasan.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String name;

    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String received = dis.readUTF();
                System.out.println();
                System.out.println("*************************************");
                System.out.println("Message from client " + getName());
                System.out.println(received);
                System.out.println("*************************************");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
