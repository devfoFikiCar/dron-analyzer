package com.devfoFikiCar.GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class pTimeGraph extends JFrame{
    private JPanel panel1;
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> alts = new ArrayList<>();

    public pTimeGraph(String title) {
        super(title);

        readData();

        // Create dataset
        DefaultCategoryDataset dataset = createDataset();
        // Create chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Pitch change over time", // Chart title
                "Time", // X-Axis Label
                "Pitch", // Y-Axis Label
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        setContentPane(panel);
    }

    private DefaultCategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(int i = 0; i < dates.size(); i++){
            dataset.addValue(Double.parseDouble(alts.get(i)), "Pitch", dates.get(i));
            System.out.println(alts.get(i) + " " + dates.get(i));
        }

        return dataset;
    }

    public void pTimeGraphOpen() {
        pTimeGraph graph = new pTimeGraph("Pitch/Time Graph");
        graph.setAlwaysOnTop(true);
        graph.pack();
        graph.setSize(600, 400);
        graph.setMinimumSize(new Dimension(600,400));
        graph.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        graph.setLocation(dim.width/2-graph.getSize().width/2, dim.height/2-graph.getSize().height/2);
        graph.setVisible(true);
    }

    public void readData() {
        try{
            FileInputStream fileInputStream = new FileInputStream(openLog.fullPath);
            System.out.println(openLog.fullPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;
            int n = 0;
            int preH = 0;
            while ((strLine = bufferedReader.readLine()) != null) {
                n++;
                if (n > 10) {
                    String[] s = strLine.split(",");
                    String dateS = s[0];
                    String alt = s[16];
                    dateS = dateS.split(" ")[1];
                    dates.add(dateS);
                    alts.add(alt);
                }
            }
            fileInputStream.close();
        } catch (Exception e) {}
    }
}
