package com.hasan.client;

import com.hasan.Utils;

import java.io.*;
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
    private File file;
    private long startPoint, endPoint;

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
        startPoint = dis.readLong();
        int range = dis.readInt();
        endPoint = startPoint+range;


        new Thread(() -> {
            file = new File(Utils.CLIENT_PRIME_FILE_DIR + getPrimeStorageFileName());
            if(file.exists())
                file.delete();
            try {
                if (!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fileWriter);

                long tempSP = startPoint;
                for(;tempSP<=endPoint; tempSP+=2){
                    if(Utils.isPrime(tempSP)){
                        System.out.println(sid+ "  " + selfIp+"  "+tempSP+"  Prime");
                        bw.write(String.valueOf(tempSP));
                        bw.newLine();
                        Thread.sleep(100);
                    }
                }
                bw.close();
                fileWriter.close();
                System.out.println(file.getAbsolutePath());
                sayDone();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private String getPrimeStorageFileName() {
        return startPoint + " - " + endPoint + ".txt";
    }


    /*Tell the server that,
    this client has finished checking the numbers given previously
     and is ready for new set of numbers*/
    private void sayDone() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        fileInputStream.close();
        System.out.println(bytes.length);
        dos.writeUTF(Utils.DONE_MSG);
        //System.out.println("Client: Sent done...");
        dos.writeUTF(getPrimeStorageFileName());
        dos.writeInt(bytes.length);
        //System.out.println("Client: Sent filename...");
        String received = dis.readUTF();
        //System.out.println("Client: got response... "+received);
        if(received.equals(Utils.ASK_FOR_FILE_MSG)) {
            //System.out.println("Client: sending file.........");
            dos.write(bytes);
        }
        file.delete();
        sayReady();
    }

    private void sayBye() throws IOException {
        dos.writeUTF(Utils.BYE_MSG);
    }

    /*This method is for testing purpose*/
    private void sendSomething() {
        String messages[] = {"Hi", "Hey there", "Hello server", "You on?", "Doing great", "Proud of you!"};
        new Thread(() -> {
            int convLength = Utils.getRandomNumber(messages.length-1, 1);
            while (convLength-->0){
                try {
                    dos.writeUTF(messages[Utils.getRandomNumber(messages.length-1, 0)]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
