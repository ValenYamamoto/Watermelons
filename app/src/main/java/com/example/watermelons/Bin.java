package com.example.watermelons;

import java.util.ArrayList;

public class Bin {
    private double frequency;
    private ArrayList<Double> content;
    private ArrayList<Double> weights;

    public Bin(double frequency, double first) {
        this.frequency = frequency;
        content = new ArrayList<Double>();
        content.add(first);
        weights = new ArrayList<Double>();
        weights.add(1.0);
    }

    public Bin(double frequency, double first, double weight) {
        this.frequency = frequency;
        content = new ArrayList<Double>();
        content.add(first);
        weights = new ArrayList<Double>();
        weights.add(weight);
    }

    public double getLogFrequency() {
        return frequency;
    }

    public void addFrequency(double frequency) {
        content.add(frequency);
    }

    public void addFrequency(double frequency, double weight) {
        content.add(frequency);
        weights.add(weight);
    }

    public int getContentLength() {
        return content.size();
    }

    public double[] returnArray() {
        double[] array = new double[content.size()];
        for(int i = 0; i < content.size(); i ++) {
            array[i] = content.get(i);
        }
        return array;
    }

    public String toString() {
        String s = String.format("Frequency: %.2f Contents: {  ", frequency);
        for(int i = 0; i < content.size(); i ++) {
            s += String.format("%.2f(%.2f),  ", content.get(i), weights.get(i));
        }
        s += String.format("}%n");
        return s;
    }

    public String getString() {
        String s = String.format("Frequency: %.2f, ", frequency);
        for(int i = 0; i < content.size(); i ++) {
            s += String.format("%.2f,", content.get(i));
        }
        s += String.format("%n");
        return s;
    }

    public double weightedAverage() {
        double[] data = returnArray();
        double[] normalizedWeights = redistributeWeights();
        return ArrayFunctions.weightedAverage(data, normalizedWeights);
    }

    public double[] redistributeWeights() {
        double total = sumOfWeights();
        double[] newWeights = new double[weights.size()];
        for(int i = 0; i < weights.size(); i ++) {
            newWeights[i] = weights.get(i)/total;
        }
        return newWeights;
    }

    public double sumOfWeights() {
        return ArrayFunctions.arraySum(weights);
    }
}