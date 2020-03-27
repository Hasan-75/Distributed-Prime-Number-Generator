package com.hasan.server;

import com.hasan.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable{
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String name;
    private File file;

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
    public void run() { //Communicates with clients
        while (socket.isConnected()) {
            try {
                String received = dis.readUTF();
                System.out.println();
                System.out.println("*************************************");
                System.out.println("Message from client " + getName());
                System.out.println(received);
                System.out.println("*************************************");
                if(received.equals(Utils.READY_MSG)){ //checking if client is ready for prime number detection
                    distributeNumberSet();
                }
                if (received.equals(Utils.DONE_MSG)){
                    //System.out.println("Server: Done...");
                    String generatedFileName = dis.readUTF();
                    int filesize = dis.readInt();
                    //System.out.println("Server: Got filename " + generatedFileName);
                    File generatedFile = new File(Utils.SERVER_PRIME_FILE_DIR+generatedFileName);
                    if(!generatedFile.getParentFile().exists())
                        generatedFile.getParentFile().mkdir();
                    dos.writeUTF(Utils.ASK_FOR_FILE_MSG);
                    //System.out.println("Server: Asked file");
                    byte[] bytes = new byte[filesize];
                    dis.read(bytes,0, filesize);
                    //System.out.println("Server: got file. size " + bytes.length);
                    FileOutputStream fileOutputStream = new FileOutputStream(generatedFile);
                    fileOutputStream.write(bytes);
                    if(ServerHandler.getInstance().getPrimeListUpdater() != null){
                        ServerHandler.getInstance().getPrimeListUpdater().updateList(generatedFile);
                    }
                    file.delete();
                }
            } catch (SocketException s){
                System.out.println(String.format("================= %s Disconnected ======================", getName()));
                File iFile = new File(Utils.INTERRUPTED_FILE_DIR + getName() + ".txt");
                if(!iFile.getParentFile().exists())
                    iFile.getParentFile().mkdir();
                file.renameTo(iFile);
                ServerHandler.getInstance().removeClient(this);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void distributeNumberSet() throws IOException, ParseException {
        long currentPoint;
        int range;

        File file = new File(Utils.INTERRUPTED_FILE_DIR);
        File[] files = file.listFiles();
        if(files!=null && files.length>0) {
            file = files[0];
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String str = new String(data, "UTF-8");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(str);
            currentPoint = Long.parseLong(jsonObject.get(Utils.START_POINT_TAG).toString());
            long endPoint = Long.parseLong(jsonObject.get(Utils.END_POINT_TAG).toString());
            range = (int) (endPoint - currentPoint);
            file.delete();

        }
        else {
            currentPoint = ServerHandler.getInstance().getCurrentPoint();
            range = ServerHandler.getInstance().getCurrentRange();

        }
        dos.writeLong(currentPoint);
        dos.writeInt(range);
        createClientFile(getName(), currentPoint, currentPoint+range);
    }

    private void createClientFile(String name, long currentPoint, long endPoint) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Utils.START_POINT_TAG, currentPoint);
            jsonObject.put(Utils.END_POINT_TAG, endPoint);
            file = new File(Utils.ON_PROCESSING_FILE_DIR + name + ".txt");
            if(!file.getParentFile().exists())
                file.getParentFile().mkdir();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
