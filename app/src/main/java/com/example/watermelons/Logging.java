package com.example.watermelons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logging {
    private static PrintWriter writer;
    public static void writeToFile(double[] data) {
        for(int i = 0; i < data.length; i++) {
            writer.printf("%.2f%n", data[i]);
        }
        writer.close();
    }

    public static void writeToFile(double[] data, double[] data2) {
        for(int i = 0; i < data.length; i++) {
            writer.printf("%.2f, %.2f%n", data[i], data2[i]);
        }
        writer.close();
    }

    public static void createLogFile() {
        try {
            String fileLocation = "C:\\Users\\Valen Yamamoto\\Desktop\\DFTOutputs\\" + "SoundAnalysis" + new SimpleDateFormat("MMddhhmm'.csv'").format(new Date());
            File file = new File(fileLocation);
            writer = new PrintWriter(fileLocation, "UTF-8");
            System.out.printf("Log File Created: %s%n", fileLocation);
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Creating Log File: We Got Problems");
        }
    }

    public static void createLogFile(String fileName) {
        try {
            String fileLocation = "C:\\Users\\Valen Yamamoto\\Desktop\\DFTOutputs\\" + fileName + new SimpleDateFormat("MMddhhmm'.csv'").format(new Date());
            File file = new File(fileLocation);
            writer = new PrintWriter(fileLocation, "UTF-8");
            System.out.printf("Log File Created: %s%n", fileLocation);
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Creating Log File: We Got Problems");
        }
    }
}
