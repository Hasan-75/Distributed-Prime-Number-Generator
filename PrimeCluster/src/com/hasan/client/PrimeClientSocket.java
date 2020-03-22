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
    private static int cid = 1;
    private int sid;
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
        sid = cid++;
        //sendSomething();
        sayReady();
    }

    private void sayReady() throws IOException { //tell the server that, this client is ready for detecting prime numbers
        dos.writeUTF(Utils.READY_MSG);
        getNumbers();
    }

    private void getNumbers() throws IOException { //Get the starting number from which this client will start checking
        final long startPoint = dis.readLong();
        int range = dis.readInt();
        final long endPoint = startPoint+range;


        new Thread(() -> {
            long tempSP = startPoint;
            for(;tempSP<=endPoint; tempSP+=2){
                if(Utils.isPrime(tempSP)){
                    System.out.println(sid+ "  " + selfIp+"  "+tempSP+"  Prime");
                }
                else
                    System.out.println(sid+ "  " + selfIp+"  "+tempSP+"  Not Prime");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                sayDone();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /*Tell the server that,
    this client has finished checking the numbers given previously
     and is ready for new set of numbers*/
    private void sayDone() throws IOException {
        dos.writeUTF(Utils.DONE_MSG);
        sayReady();
    }

    /*This method is for testing purpose*/
    private void sendSomething() {
        String messages[] = {"Hi", "Hey there", "Hello server", "You on?", "Doing great", "Proud of you!"};
        new Thread(() -> {
            int convLength = Utils.getRandomNumber(messages.length-1, 1);
            while (convLength-->0){
                try {
                    dos.writeUTF(messages[Utils.getRandomNumber(messages.length-1, 0)]);
                    Thread.sleep(Utils.getRandomNumber(5, 2)*1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
