package com.hasan.server.gui;

import com.hasan.server.MainServerUI;
import com.hasan.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerUI {
    private static Dimension frameDimension = new Dimension(700, 400);
    private static Point frameLocation = new Point(100, 100);

    private ServerUI serverUI;

    private JFrame mainFrame;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private Container mainContainer;

    private JLabel ipLabel;
    private JLabel portLabel;

    private JButton btnChangePort;
    private JButton btnStartServer;

    private JList clientList;
    private JList primeList;
    private DefaultListModel<String> clientListModel = new DefaultListModel<>();
    private DefaultListModel<String> primeListModel = new DefaultListModel<>();

    public ServerUI() {
        serverUI = this;
    }

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
        mainFrame = new JFrame(Utils.TITLE_SERVER_FRAME);
        mainFrame.setSize(frameDimension);
        mainFrame.setLocation(frameLocation);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainContainer = mainFrame.getContentPane();
        mainContainer.setLayout(new BorderLayout(8,6));
        mainContainer.setBackground(new Color(243, 250, 255));
        //mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, new Color(222, 11, 251)));

        createTopPanel();
        addListeners();
        mainFrame.setVisible(true);
    }


    private void createTopPanel() {
        topPanel = new JPanel();
        mainFrame.add(topPanel, BorderLayout.NORTH);
        ipLabel = new JLabel("Server IP - 0.0.0.0", SwingConstants.CENTER);
        portLabel = new JLabel("Port - " + Utils.COMMON_PORT, SwingConstants.CENTER);
        btnChangePort = new JButton("Change Port");
        btnStartServer = new JButton("Start Server");
        GridBagConstraints gbc = new GridBagConstraints();
        topPanel.setLayout(new GridBagLayout());

        gbc.insets = new Insets(5,20,5,20);
        gbc.gridx = 3;
        gbc.gridy = 2;
        topPanel.add(ipLabel, gbc);

        gbc.gridx = 6;
        gbc.gridy = 2;
        topPanel.add(portLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(btnChangePort, gbc);

        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(btnStartServer, gbc);

        topPanel.setBorder(new LineBorder(Color.black));
        topPanel.setBackground(Utils.COLOR_TOP_PANEL_BG);

        createMainPanel();
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(new LineBorder(Color.black, 1));
        mainPanel.setLayout(new GridLayout(1, 2, 5,5));

        leftPanel = new JPanel();
        leftPanel.setBorder(new LineBorder(Color.black, 2));
        leftPanel.setBackground(Color.white);

        rightPanel = new JPanel();
        rightPanel.setBorder(new LineBorder(Color.black, 2));
        rightPanel.setBackground(Color.white);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        mainFrame.add(mainPanel, BorderLayout.CENTER);

        initLeftPanel();
        initRightPanel();

    }

    private void initLeftPanel() {
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        primeList = new JList(primeListModel);
        JScrollPane jScrollPane = new JScrollPane(primeList);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        leftPanel.add(new JLabel("Prime Numbers", SwingConstants.CENTER), gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        leftPanel.add(jScrollPane, gbc);
    }

    private void initRightPanel() {
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        clientList = new JList(clientListModel);
        JScrollPane jScrollPane = new JScrollPane(clientList);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        rightPanel.add(new JLabel("Connected Clients", SwingConstants.CENTER), gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        rightPanel.add(jScrollPane, gbc);

    }

    public void updateIp(String ip){
        ipLabel.setText(ip);
    }

    public void addClientToList(String client){
        clientListModel.addElement(client);
        //clientList.updateUI();
    }

    public void removeClientFromList(String client) {
        clientListModel.removeElement(client);
    }

    public void updateClientList() {
        primeList.setModel(primeListModel);
        rightPanel.repaint();
        rightPanel.revalidate();
    }

    public void addPrimeToList(String prime){
        primeListModel.addElement(prime);
        //primeList.updateUI();
    }

    public void updatePrimeList(){
        primeList.setModel(primeListModel);

        leftPanel.repaint();
        leftPanel.revalidate();
    }

    private void addListeners() {
        btnStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btnStartServer.setEnabled(false);
                MainServerUI.startServer(serverUI);
            }
        });
    }

}
