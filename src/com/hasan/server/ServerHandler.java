package com.hasan.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerHandler{
    private List<ClientHandler> clientList;
    private long currentPoint = 3;
    private int range = 1000;
    private static ServerHandler instance;

    private ServerHandler(){

    }

    public static ServerHandler getInstance(){
        if(instance==null)
            instance = new ServerHandler();
        return instance;
    }
    public static void startServer(int port) throws IOException {
        PrimeServerSocket ss=new PrimeServerSocket(port);
        System.out.println("====================================");
        System.out.println("Starting server at " + Inet4Address.getLocalHost() + ":6666");
        System.out.println("====================================");
        System.out.println();
        ServerHandler serverHandler = ServerHandler.getInstance();

        while (true){
            Socket s=ss.accept();//establishes connection
            DataInputStream dis=new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            ClientHandler clientHandler = new ClientHandler(s, dis, dos); //Creating a ClientHandler for each client

            serverHandler.addClient(clientHandler); //Adding the newly connected client's ClientHandler to a list
            /*if(serverHandler.getClientList().size()>10){
                System.out.println("====================================");
                System.out.println("Closing Server");
                System.out.println("====================================");
                System.out.println();
                ss.close();
            }*/
        }
 
    }

    public void addClient(ClientHandler clientHandler){
        getClientList().add(clientHandler);
        clientHandler.setName(""+((char)(getClientList().size()+65)));
        System.out.println("====================================");
        System.out.println("Adding new Client " + clientHandler.getName() + "----" + clientHandler.getSocket().getInetAddress());
        System.out.println("Number of clients are " + getClientList().size());
        System.out.println("====================================");
        System.out.println();
        new Thread(clientHandler).start(); //Starting a thread with the newly added ClientHandler Runnable implemented class
    }

    public List<ClientHandler> getClientList() {
        if(clientList==null)
            clientList = new ArrayList<>();
        return clientList;
    }

    synchronized public long getCurrentPoint(){ //determining the current available number from which a new client will start checking prime
        currentPoint+=range;
        return currentPoint-range;
    }

    synchronized public int getCurrentRange() {
        return range;
    }
}
