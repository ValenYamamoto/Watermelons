package com.example.watermelons;

import java.util.ArrayList;

public class ArrayFunctions {
    public static double[] normalize(double[] data) {
        double max = data[0];
        double min = data[0];
        for(int i = 1; i < data.length; i ++) {
            if(data[i] > max) {
                max = data[i];
            } else if(data[i] < min) {
                min = data[i];
            }
        }
        double delta = max - min;
        for(int i = 0; i < data.length; i++) {
            data[i] = (data[i] - min)/max;
        }
        return data;
    }

    public static double[] normalize(ArrayList<Double> list) {
        double[] data = toArray(list);
        double max = data[0];
        double min = data[0];
        for(int i = 1; i < data.length; i ++) {
            if(data[i] > max) {
                max = data[i];
            } else if(data[i] < min) {
                min = data[i];
            }
        }
        double delta = max - min;
        for(int i = 0; i < data.length; i++) {
            data[i] = (data[i] - min)/max;
        }
        return data;
    }

    public static double[] toArray(ArrayList<Double> list) {
        double[] array = new double[list.size()];
        for(int i = 0; i < list.size(); i ++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static double arraySum(double[] data) {
        double sum = 0;
        for(int i = 0; i < data.length; i ++) {
            sum += data[i];
        }
        return sum;
    }

    public static double arraySum(ArrayList<Double> data) {
        double sum = 0;
        for(int i = 0; i < data.size(); i ++) {
            sum += data.get(i);
        }
        return sum;
    }

    public static double weightedAverage(double[] data, double[] weights) {
        double sum = 0;
        for(int i = 0; i < data.length; i ++) {
            sum += data[i] * weights[i];
        }
        return sum;
    }

    public static double rmsArray(double[] audio) {
        double sum = 0;
        for (int i = 0; i < audio.length; i ++) {
            sum += audio[i] * audio[i];
        }
        sum /= audio.length;
        sum = Math.sqrt(sum);
        return sum;
    }

    public static double rmsArray(short[] audio) {
        double sum = 0;
        for (int i = 0; i < audio.length; i ++) {
            sum += audio[i] * audio[i];
        }
        sum /= audio.length;
        sum = Math.sqrt(sum);
        return sum;
    }

    public static double avgArray(double[] audio) {
        double sum = 0;
        for (int i = 0; i < audio.length; i ++) {
            sum += audio[i];
        }
        sum /= audio.length;
        return sum;
    }

    public static double absAvgArray(double[] audio) {
        double sum = 0;
        for (int i = 0; i < audio.length; i ++) {
            sum += Math.abs(audio[i]);
        }
        sum /= audio.length;
        return sum;
    }

    public static double stdDev(double[] data) {
        double avg = avgArray(data);
        double sum = 0;
        for(int i = 0; i < data.length; i ++) {
            sum += Math.pow(avg - data[i], 2);
        }
        sum /= data.length - 1;
        sum = Math.sqrt(sum);
        return sum;
    }


}
