package com.hasan.client.gui;

import com.hasan.client.MainClientUI;
import com.hasan.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private Dimension frameDimension = new Dimension(600, 400);
    private Point frameLocation = new Point(100,100);
    private JButton btnConnect;

    public void createFrame(){
        for (javax.swing.UIManager.LookAndFeelInfo info :  javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        mainFrame = new JFrame(Utils.TITLE_CLIENT_FRAME);
        mainFrame.setSize(frameDimension);
        mainFrame.setLocation(frameLocation);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createPanel();
        addListeners();
        mainFrame.setVisible(true);
    }

    private void createPanel() {
        mainPanel = new JPanel();
        btnConnect = new JButton("Connect to server");
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(btnConnect, gbc);
        mainFrame.add(mainPanel);
    }

    private void addListeners() {
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String ip = JOptionPane.showInputDialog("Input IP Address of server");
                String port = JOptionPane.showInputDialog("Input PORT Address of server");
                int opt = JOptionPane.showConfirmDialog(null,
                        "Are you sure that you want to connect to "+ip+":"+port,
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if(opt==0){
                    MainClientUI.connectToServer(ip, Integer.parseInt(port));
                }
            }
        });
    }
}
