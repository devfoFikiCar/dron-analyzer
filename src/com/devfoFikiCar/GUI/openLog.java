package com.devfoFikiCar.GUI;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

public class openLog extends Application {
    private JButton btOpenLogFile;
    private JPanel panel1;
    private JLabel lTitle;
    private JFileChooser openFileChooser;

    String homeFilePath = System.getProperty("user.home");
    public static String fullPath = "";

    private static JFrame frame = new JFrame("DronAnalyzer");

    public openLog() {
        btOpenLogFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileChooser = new JFileChooser();
                openFileChooser.setCurrentDirectory(new File(homeFilePath));
                openFileChooser.setFileFilter(new FileNameExtensionFilter("Log files", "log"));
                int returnValue = openFileChooser.showOpenDialog(panel1);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File cFile = openFileChooser.getSelectedFile();
                    fullPath = cFile.getAbsolutePath();
                    mainView mainView = new mainView();
                    mainView.setLogFilePath(fullPath);
                    System.out.println(fullPath);
                    mainView.mainViewOpen();
                    frame.dispose();
                } else {
                    System.out.println("Wrong file");
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fileLogOpen();
    }

    public void fileLogOpen() {
        frame.setContentPane(new openLog().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.setMaximumSize(new Dimension(500, 500));
        frame.setMinimumSize(new Dimension(250, 250));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
