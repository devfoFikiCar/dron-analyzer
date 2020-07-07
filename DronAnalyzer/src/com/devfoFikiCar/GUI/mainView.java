package com.devfoFikiCar.GUI;

import sun.jvm.hotspot.utilities.Assert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class mainView {
    private JButton btTableView;
    private JPanel panel1;
    private JButton bt3DViz;
    private JButton btKML;
    private JButton btATGraph;
    private JButton btPTime;
    private JButton btRTime;
    private JButton btYTime;
    private JButton btVATime;
    private static JFrame frame = new JFrame("Control Panel");

    private String logFilePath = "";
    private String desktopPath = System.getProperty("user.home") + "/Desktop/output.kml";
    private String desktopPath1 = System.getProperty("user.home") + "/Desktop/dataPY.txt";

    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> cords = new ArrayList<>();
    private ArrayList<String> header1 = new ArrayList<>();
    private ArrayList<String> header2 = new ArrayList<>();
    private ArrayList<String> footer1 = new ArrayList<>();
    private ArrayList<String> footer2 = new ArrayList<>();

    public mainView() {
        btTableView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableView tableView = new tableView();
                tableView.setLogFilePath(logFilePath);
                tableView.tableViewOpen();
            }
        });
        bt3DViz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    write3D();

                    String pythonInterpreter = "/usr/bin/python3" ; // default

                    InputStream script = mainView.class.getResourceAsStream("vizualize3D.py");
                    Process pythonProcess = new ProcessBuilder(pythonInterpreter, "-")
                            .start();

                    new Thread(() ->  {
                        try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(pythonProcess.getInputStream()))) {

                            for (String line ; (line = reader.readLine()) != null ;) {
                                System.out.println(line);
                            }
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    }).start();

                    OutputStream stdin = pythonProcess.getOutputStream();
                    byte[] buffer = new byte[1024];
                    for (int read = 0 ; read >= 0 ; read = script.read(buffer)) {
                        stdin.write(buffer, 0, read);
                    }
                    stdin.close();

                } catch (Exception ex) { bt3DViz.setText(ex.getMessage()); }
            }
        });
        btKML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileKML();
                System.out.println("DONE");
            }
        });
        btATGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                altTimeGraph altTimeGraph = new altTimeGraph("test");
                altTimeGraph.altTimeGraphOpen();
            }
        });
        btPTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pTimeGraph pTimeGraph = new pTimeGraph("test");
                pTimeGraph.pTimeGraphOpen();
            }
        });
        btRTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rTimeGraph rTimeGraph = new rTimeGraph("test");
                rTimeGraph.rTimeGraphOpen();
            }
        });
        btYTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yTimeGraph yTimeGraph = new yTimeGraph("test");
                yTimeGraph.yTimeGraphOpen();
            }
        });
        btVATime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vATimeGraph vATimeGraph = new vATimeGraph("test");
                vATimeGraph.vATimeGraphOpen();
            }
        });
    }

    public void mainViewOpen() {
        frame.setContentPane(new mainView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(750, 500));
        frame.setMinimumSize(new Dimension(500, 500));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    public void write3D() {
        ArrayList<String> data = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(openLog.fullPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            int n = 0;
            while ((strLine = bufferedReader.readLine()) != null) {
                n++;
                if (n > 10) {
                    String[] s = strLine.split(",");
                    data.add(s[12] + " " + s[10] + " " + s[13]);
                }
            }
            fileInputStream.close();
        } catch (Exception ex) {
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath1);
            for (int i = 0; i < data.size(); i++) {
                myWriter.write(data.get(i) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileKML(){

        readDateCord();
        readHeadersAndFooter();

        String s1 = "<Placemark>";
        String s2 = "<TimeStamp><when> </when></TimeStamp>" ;
        String s3 = "<styleUrl>#starting</styleUrl>";
        String s4 = "<Point><coordinates> </coordinates></Point>";
        String s5 = "</Placemark>";
        String s6 = "<styleUrl>#landing</styleUrl>";
        String sWhen = "<when> </when>";
        String sCord = "<gx:coord> </gx:coord>";

        try {
            FileWriter myWriter = new FileWriter(desktopPath);
            for (int i = 0; i < header1.size(); i++) {
                myWriter.write(header1.get(i) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath, true);
            myWriter.write(s1 + "\n");
            myWriter.write(s2.replace(" ", dates.get(0)) + "\n");
            myWriter.write(s3 + "\n");
            myWriter.write(s4.replace(" ", cords.get(0)) + "\n");
            myWriter.write(s5 + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath, true);
            for (int i = 0; i < header2.size(); i++) {
                myWriter.write(header2.get(i) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath, true);
            for (String s : dates){
                myWriter.write(sWhen.replace(" ", s) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath, true);
            for (String s : cords){
                myWriter.write(sCord.replace(" ", s) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath, true);
            for (int i = 0; i < footer1.size(); i++) {
                myWriter.write(footer1.get(i) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath, true);
            myWriter.write(s1 + "\n");
            myWriter.write(s2.replace(" ", dates.get(dates.size() - 1)) + "\n");
            myWriter.write(s6 + "\n");
            myWriter.write(s4.replace(" ", cords.get(cords.size() - 1)) + "\n");
            myWriter.write(s5 + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(desktopPath, true);
            for (int i = 0; i < footer2.size(); i++) {
                myWriter.write(footer2.get(i) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDateCord(){
        try {
            FileInputStream fileInputStream = new FileInputStream(openLog.fullPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            int n = 0;
            while ((strLine = bufferedReader.readLine()) != null) {
                n++;
                if (n > 10) {
                    String[] s = strLine.split(",");
                    String s1 = s[10];
                    String s2 = s[12];
                    String s3 = s[13];
                    StringBuffer sb1 = new StringBuffer(s2);
                    sb1.insert(s2.length() - 7, ".");
                    StringBuffer sb2 = new StringBuffer(s3);
                    sb2.insert(s3.length() - 7, ".");
                    double d = Double.parseDouble(s1);
                    String s1f = String.valueOf(Math.ceil(10.0 * d / 100.0) / 10.0);
                    cords.add(sb2.toString() + " " + sb1.toString() + " " + s1f);
                    String dateT = s[0];
                    dateT = dateT.replaceAll(" ", "");
                    StringBuffer stringBuffer = new StringBuffer(dateT);
                    stringBuffer.insert(10, "T");
                    stringBuffer.append("Z");
                    dates.add(stringBuffer.toString());
                }
            }
            fileInputStream.close();
        } catch (Exception ex) {
        }
    }

    public void readHeadersAndFooter(){
        try {
            InputStream in = mainView.class.getResourceAsStream("header1.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                header1.add(strLine);
            }
        } catch (Exception ex) {
        }

        try {
            InputStream in = mainView.class.getResourceAsStream("header2.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                header2.add(strLine);
            }
        } catch (Exception ex) {
        }

        try {
            InputStream in = mainView.class.getResourceAsStream("footer1.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                footer1.add(strLine);
            }
        } catch (Exception ex) {
        }

        try {
            InputStream in = mainView.class.getResourceAsStream("footer2.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                footer2.add(strLine);
            }
        } catch (Exception ex) {
        }
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
}
/*
*
* These are the methods i tried to run py file from jar, it is in there 100%, yet none works:
```
InputStream script = mainView.class.getResourceAsStream("vizualize3D.py");
Process process = new ProcessBuilder("python3", "-").start() ;

Process p1 = Runtime.getRuntime().exec("python3 " + script);

Runtime rt = Runtime.getRuntime();
Process pr = rt.exec("python3 " + mainView.class.getResourceAsStream("vizualize3D.py"));
```*/