package com.hasan.server.gui;

import com.hasan.Utils;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PrimeListUpdater{
    private ServerUI serverUI;

    public PrimeListUpdater(ServerUI serverUI) {
        this.serverUI = serverUI;
    }

    public void updateList(){
        File primeDir = new File(Utils.SERVER_PRIME_FILE_DIR);
        if(!primeDir.exists() || primeDir.listFiles().length<=0)
            return;
        List<File> fileList = Arrays.asList(primeDir.listFiles());
        fileList.sort(Utils.getFileComparator());

        if(primeDir.exists()) {
            for (File file : fileList) {
                try {
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        serverUI.addPrimeToList(line);
                        //System.out.println(line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        serverUI.updatePrimeList();
    }

    public void updateList(File file){
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                serverUI.addPrimeToList(line);
                //System.out.println(line);
            }
            serverUI.updatePrimeList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
