package com.hasan.server;

import com.hasan.Utils;
import com.hasan.client.PrimeClientSocket;
import com.hasan.server.ServerHandler;
import com.hasan.server.gui.PrimeListUpdater;
import com.hasan.server.gui.ServerUI;

import java.io.File;
import java.io.IOException;

public class MainServerUI {

    public static void main(String[] args) {

        ServerUI serverUI = new ServerUI();
        serverUI.createFrame();
    }

    public static void startServer(ServerUI serverUI) {
        PrimeListUpdater primeListUpdater = new PrimeListUpdater(serverUI);
        File file = new File(Utils.PARENT_DIR);
        if(!file.exists())
            file.mkdir();

        Thread t1 = new Thread(() -> {
            try {
                ServerHandler.startServer(Utils.COMMON_PORT, primeListUpdater, serverUI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        primeListUpdater.updateList();

    }
}
