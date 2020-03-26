package com.hasan.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PrimeServerSocket extends ServerSocket {

    private int port;

    public PrimeServerSocket(int port) throws IOException {
        super(port);
        this.port = port;
    }

    public int getPort() {
        return port;
    }

}
