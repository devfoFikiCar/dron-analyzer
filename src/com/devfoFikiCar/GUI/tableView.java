package com.devfoFikiCar.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class tableView {
    private JPanel panel1;
    private JScrollPane scrollPane;
    private JTable table1;

    private String logFilePath = "";
    private static ArrayList<String[]> stringLogData = new ArrayList<>();

    private String colNames[] = {"UTC", "TimeStamp", "flightMode", "distance", "height", "loseGPSAct", "goHomeHeight",
            "maxHeight", "maxDistance", "maxSpeed", "alt", "IMU_Sta", "lat", "lon", "AutoTakeOFF", "roll", "pitch", "yaw", "motorstatus", "errorFlag", "nsat", "voltage"};

    private Object[][] dataLog;

    public void tableViewOpen() {
        readLog();
        dataLog = new Object[stringLogData.size()][23];

        for (int i = 0; i < stringLogData.size(); i++){
            for(int j = 0; j < 22; j++){
                dataLog[i][j] = stringLogData.get(i)[j];
            }
        }

        // TODO: Optimize table column sizes

        table1 = new JTable(dataLog, colNames);
        table1.getColumn("UTC").setMinWidth(175);
        table1.getColumn("lat").setMinWidth(75);
        table1.getColumn("lon").setMinWidth(75);
        table1.getColumn("yaw").setMinWidth(40);

        JFrame frame = new JFrame("tableView");
        frame.setContentPane(new tableView().panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1100, 500));
        frame.setMinimumSize(new Dimension(500,200));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
        frame.add(new JScrollPane(table1));
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = openLog.fullPath;
    }

    public void readLog() {
        try {
            FileInputStream fileInputStream = new FileInputStream(logFilePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            int n = 0;
            while ((line = bufferedReader.readLine()) != null) {
                n++;
                if (n > 9) {
                    String lineS[] = line.split(",");
                    stringLogData.add(lineS);
                }
            }
            fileInputStream.close();
        } catch (Exception ex) {
        }
    }
}
