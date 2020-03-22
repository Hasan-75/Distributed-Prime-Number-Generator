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

    public static void startServer(int port) throws IOException {
        PrimeServerSocket ss=new PrimeServerSocket(port);
        System.out.println("====================================");
        System.out.println("Starting server at " + Inet4Address.getLocalHost() + ":6666");
        System.out.println("====================================");
        System.out.println();
        ServerHandler serverHandler = new ServerHandler();

        while (true){
            Socket s=ss.accept();//establishes connection
            DataInputStream dis=new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            ClientHandler clientHandler = new ClientHandler(s, dis, dos);

            serverHandler.addClient(clientHandler);
            if(serverHandler.getClientList().size()>10){
                System.out.println("====================================");
                System.out.println("Closing Server");
                System.out.println("====================================");
                System.out.println();
                ss.close();
            }
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
        new Thread(clientHandler).start();
    }

    public List<ClientHandler> getClientList() {
        if(clientList==null)
            clientList = new ArrayList<>();
        return clientList;
    }

}
