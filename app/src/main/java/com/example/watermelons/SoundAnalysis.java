package com.example.watermelons;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class SoundAnalysis {
    private final static double C0 = 16.35;
    private final static double CSharp0 = 17.32;
    private final static double D0 = 18.35;
    private final static double DSharp0 = 19.45;
    private final static double E0 = 20.60;
    private final static double F0 = 21.83;
    private final static double FSharp0 = 23.12;
    private final static double G0 = 24.50;
    private final static double GSharp0 = 25.96;
    private final static double A0 = 27.50;
    private final static double ASharp0 = 29.14;
    private final static double B0 = 30.87;

    private final static double C1 = 30.70;
    private final static double CSharp1 = 34.65;
    private final static double D1 = 36.71;
    private final static double DSharp1 = 38.89;
    private final static double E1 = 41.20;
    private final static double F1 = 43.61;
    private final static double FSharp1 = 46.25;
    private final static double G1 = 49.00;
    private final static double GSharp1 = 51.91;
    private final static double A1 = 55.00;
    private final static double ASharp1 = 58.27;
    private final static double B1 = 61.74;

    private final static double C2 = 65.41;
    private final static double CSharp2 = 69.30;
    private final static double D2 = 73.42;
    private final static double DSharp2 = 77.78;
    private final static double E2 = 82.41;
    private final static double F2 = 87.31;
    private final static double FSharp2 = 92.50;
    private final static double G2 = 98.00;
    private final static double GSharp2 = 103.83;
    private final static double A2 = 110.0;
    private final static double ASharp2 = 116.54;
    private final static double B2 = 123.47;

    private final static double C3 = 130.81;
    private final static double CSharp3 = 138.59;
    private final static double D3 = 146.83;
    private final static double DSharp3 = 155.56;
    private final static double E3 = 164.81;
    private final static double F3 = 174.61;
    private final static double FSharp3 = 185.00;
    private final static double G3 = 196.00;
    private final static double GSharp3 = 207.65;
    private final static double A3 = 220.00;
    private final static double ASharp3 = 233.08;
    private final static double B3 = 246.94;

    private final static double C4 = 261.63;
    private final static double CSharp4 = 277.18;
    private final static double D4 = 293.66;
    private final static double DSharp4 = 311.13;
    private final static double E4 = 329.63;
    private final static double F4 = 349.23;
    private final static double FSharp4 = 369.99;
    private final static double G4 = 392.00;
    private final static double GSharp4 = 415.30;
    private final static double A4 = 440;
    private final static double ASharp4 = 446.16;
    private final static double B4 = 493.88;

    private final static double C5 = 523.25;
    private final static double CSharp5 = 554.37;
    private final static double D5 = 587.33;
    private final static double DSharp5 = 622.25;
    private final static double E5 = 659.25;
    private final static double F5 = 698.46;
    private final static double FSharp5 = 739.99;
    private final static double G5 = 783.99;
    private final static double GSharp5 = 830.61;
    private final static double A5 = 880;
    private final static double ASharp5 = 932.33;
    private final static double B5 = 987.77;

    private final static double C6 = 1046.50;
    private final static double CSharp6 = 1108.73;
    private final static double D6 = 1174.66;
    private final static double DSharp6 = 1244.51;
    private final static double E6 = 1318.51;
    private final static double F6 = 1396.91;
    private final static double FSharp6 = 1479.98;
    private final static double G6 = 1567.98;
    private final static double GSharp6 = 1661.22;
    private final static double A6 = 1760.00;
    private final static double ASharp6 = 1864.66;
    private final static double B6 = 1975.53;


    private static final double[][] noteFrequencies = {
            {C0, C1, C2, C3, C4, C5, C6},
            {CSharp0, CSharp1, CSharp2, CSharp3, CSharp4, CSharp5, CSharp6},
            {D0, D1, D2, D3, D4, D5, D6},
            {DSharp0, DSharp1, DSharp2, DSharp3, DSharp4, DSharp5, DSharp6},
            {E0, E1, E2, E3, E4, E5, E6},
            {F0, F1, F2, F3, F4, F5, F6},
            {FSharp0, FSharp1, FSharp2, FSharp3, FSharp4, FSharp5, FSharp6},
            {G0, G1, G2, G3, G4, G5, G6},
            {GSharp0, GSharp1, GSharp2, GSharp3, GSharp4, GSharp5, GSharp6},
            {A0, A1, A2, A3, A4, A5, A6},
            {ASharp0, ASharp1, ASharp2, ASharp3, ASharp4, ASharp5, ASharp6},
            {B0, B1, B2, B3, B4, B5, B6}
    };

    private static final String[][] noteNames = {
            {"C0", "C1", "C2", "C3", "C4", "C5", "C6"},
            {"CSharp0", "CSharp1", "CSharp2", "CSharp3", "CSharp4", "CSharp5", "CSharp6"},
            {"D0", "D1", "D2", "D3", "D4", "D5", "D6"},
            {"DSharp0", "DSharp1", "DSharp2", "DSharp3", "DSharp4", "DSharp5", "DSharp6"},
            {"E0", "E1", "E2", "E3", "E4", "E5", "E6"},
            {"F0", "F1", "F2", "F3", "F4", "F5", "F6"},
            {"FSharp0", "FSharp1", "FSharp2", "FSharp3", "FSharp4", "FSharp5", "FSharp6"},
            {"G0", "G1", "G2", "G3", "G4", "G5", "G6"},
            {"GSharp0", "GSharp1", "GSharp2", "GSharp3", "GSharp4", "GSharp5", "GSharp6"},
            {"A0", "A1", "A2", "A3", "A4", "A5", "A6"},
            {"ASharp0", "ASharp1", "ASharp2", "ASharp3", "ASharp4", "ASharp5", "ASharp6"},
            {"B0", "B1", "B2", "B3", "B4", "B5", "B6"}
    };

    private static int BACKGROUND_SOUND_THRESHOLD = 200;

    private static int finalEndIndex;
    private static int finalStartIndex;


    private static short lastAmp;

    public static String closestNoteFrequency(double freq) {
        double min = -1;
        String noteName = "";
        for(int i = 0; i < noteFrequencies.length; i ++) {
            for(int j = 0; j < noteFrequencies[i].length; j ++) {
                double diff = Math.abs(freq - noteFrequencies[i][j]);
                if(min == -1) {
                    min = diff;
                    noteName = noteNames[i][j];
                } else  if(diff < min) {
                    min = diff;
                    noteName = noteNames[i][j];
                }
            }
        }
        if(noteName == "") {
            noteName = "No Note Found";
        }
        return noteName;
    }

    public static double[][] dft(double[] inreal, short[] inimag) {
        int n = inimag.length;

        double[] outreal = new double[n];
        double[] outimag = new double[n];

        for(int k = 0; k < n; k++) {
            double sumreal = 0;
            double sumimag = 0;

            for (int t = 0; t < n; t++) {
                double angle = 2 * Math.PI * t * k;
                angle = angle / n;

                sumreal += inimag[t] * Math.cos(angle);
                sumimag += inimag[t] * Math.sin(angle);
            }
            outreal[k] = sumreal;
            outimag[k] = sumimag;
        }
//        System.out.println("Printing DFT Output:");
        double[] freq = new double[n/2];
        for (int i = 0; i < freq.length; i ++) {
            freq[i] = 4 * (outreal[i] * outreal[i]) + 4 * (outimag[i] * outimag[i]);
            freq[i] /= n;
        }

        double[][] finalOutput = new double[n/2][2];
        for(int i = 0; i < finalOutput.length; i ++) {
            finalOutput[i][0] = i * MainActivity.samplingRate/n;
            finalOutput[i][1] = freq[i];
        }
        return finalOutput;
    }

    public static short[] getSound(short[] audio) {
        Log.d("getSound", "Starting");
        Log.d("getSound", "Array Length: " + audio.length);
        lastAmp = 0;
        int startIndex = -1;
        for (int i = 0; i < audio.length; i++) {
            if (startWave(audio[i])) {
                if (startIndex != -1) {
//                   rmsArray(Arrays.copyOfRange(audio, startIndex, i + 1));
                    int localMax = findLocalMax(Arrays.copyOfRange(audio, startIndex, i + 1));
                    if (localMax > BACKGROUND_SOUND_THRESHOLD) {

                        break;
                    }
                }
                startIndex = i;
            }
        }
        if (startIndex == -1) {
            startIndex = 0;
        }
        lastAmp = 0;
        int endIndex = -1;
        for (int i = startIndex; i < audio.length; i++) {
            if (endWave(audio[i])) {
                if (endIndex != -1) {
                    int localMax = findLocalMax((Arrays.copyOfRange(audio, endIndex, i + 1)));
                    if (localMax < BACKGROUND_SOUND_THRESHOLD) {

                        break;
                    }
                }
                endIndex = i;
            }
        }
        if (endIndex == -1) {
            endIndex = audio.length - 1;
        }
        Log.d("Finding Sound Bite", "Start: " + startIndex + "    End: " + endIndex);
        if (startIndex != endIndex) {
            return Arrays.copyOfRange(audio, startIndex, endIndex + 1);
        }

        return audio;
    }

    public static short[] getSoundRMS(short[] audio) {
        Log.d("getSound", "Starting");
//        Log.d("getSound", "Array Length: " + audio.length);
        double rmsFullArray = ArrayFunctions.rmsArray(audio);
//        Log.d("getSound", "Array RMS: " + rmsFullArray);
        int startIndex = -1;
        for (int i = 0; i < audio.length; i++) {
            if (startWave(audio[i])) {
                if (startIndex != -1) {
//                   rmsArray(Arrays.copyOfRange(audio, startIndex, i + 1));
                    double localrms = ArrayFunctions.rmsArray(Arrays.copyOfRange(audio, startIndex, i + 1));
                    if (localrms > rmsFullArray) {

                        break;
                    }
                }
                startIndex = i;
            }
        }
        if (startIndex == -1) {
            startIndex = 0;
        }
        lastAmp = 0;
        int endIndex = -1;
        for (int i = startIndex; i < audio.length; i++) {
            if (endWave(audio[i])) {
                if (endIndex != -1) {
                    double localrms = ArrayFunctions.rmsArray(Arrays.copyOfRange(audio, startIndex, i + 1));
                    if (localrms < rmsFullArray) {

                        break;
                    }
                }
                endIndex = i;
            }
        }
        if (endIndex == -1) {
            endIndex = audio.length - 1;
        }
//        Log.d("Finding Sound Bite", "Start: " + startIndex + "    End: " + endIndex);
        if (startIndex != endIndex) {
            finalEndIndex = endIndex;
            return Arrays.copyOfRange(audio, startIndex, endIndex + 1);
        }
        finalEndIndex = audio.length-1;
        return audio;
    }

    private static int findLocalMax(short[] data) {
        int max = 0;
        for (int i = 0; i < data.length; i ++) {
            if(Math.abs(data[i]) > max ) {
                max = Math.abs(data[i]);
            }
        }
        return max;
    }

    public static short[] trimAudio(short[] audio) {
        double rmsFullArray = ArrayFunctions.rmsArray(audio);
        int startIndex = -1;
        for (int i = 0; i < audio.length; i++) {
            if (startWave(audio[i])) {
                if (startIndex != -1) {
                    int localMax = findLocalMax(Arrays.copyOfRange(audio, startIndex, i + 1));
                    if (localMax > rmsFullArray) {

                        break;
                    }
                }
                startIndex = i;
            }
        }
        if (startIndex == -1) {
            startIndex = 0;
        }
        finalStartIndex = startIndex;
        lastAmp = 0;
        int endIndex = -1;
        for (int i = startIndex; i < audio.length; i++) {
            if (endWave(audio[i])) {
                if (endIndex != -1) {
                    double localMax = findLocalMax(Arrays.copyOfRange(audio, endIndex, audio.length));
                    if (localMax < rmsFullArray) {
                        endIndex = i;
                        break;
                    }
                }
                endIndex = i;
            }
        }
        if (endIndex == -1 || endIndex == 0) {
            endIndex = audio.length - 1;
        }
        System.out.printf("Finding Sound Bite Start: %d    End: %d%n", startIndex, endIndex);
        if (startIndex != endIndex) {
            finalEndIndex = endIndex;
            return Arrays.copyOfRange(audio, startIndex, endIndex + 1);
        }
//  	  	System.out.println("Hi Here");
        finalEndIndex = endIndex;
        return audio;
    }

    private static boolean startWave(short audio) {
        if ((lastAmp < 0 && audio >= 0) || (lastAmp <= 0 && audio > 0)) {
            lastAmp = audio;
            return true;
        }
        lastAmp = audio;
        return false;
    }

    private static boolean endWave(short audio) {
        if ((lastAmp > 0 && audio <= 0) || (lastAmp >= 0 && audio < 0)) {
            lastAmp = audio;
            return true;
        }
        lastAmp = audio;
        return false;
    }

    public static double findMaxDFT(double[][] data) {
        double max = 0;
        double index = 0;
        for(int i = 1; i < data.length; i ++) {
            if (data[i][1] > max) {
                index = data[i][0];
                max = data[i][1];
            }
        }
        return index;
    }

//    public static double[] getHighlightAvg(short[] audio) {
//        System.out.println("Starting Selection");
//        double[] bestCut = getSoundAbsAvg(audio);
//        finalEndIndex = 0;
//        boolean done = false;
//        while(!done) {
//            if(finalEndIndex != audio.length - 1) {
//                System.out.printf("Final End Index: %d%n", finalEndIndex);
//                audio = Arrays.copyOfRange(audio, finalEndIndex, audio.length);
//                double[] newCut = getSoundAbsAvg(audio);
//                if(ArrayFunctions.absAvgArray(bestCut) < ArrayFunctions.absAvgArray(newCut)) {
//                    bestCut = newCut;
//                }
//            } else {
//                done = true;
//            }
////    		System.out.printf("Number of Loops in getHighlightAvg: %d%n", loops);
////    		loops++;
//        }
//        return bestCut;
//    }

    public static short[] getHighlightRMS(short[] audio) {
        short[] bestCut = getSoundRMS(audio);
        finalEndIndex = 0;
        int loops = 0;
        int startIndex = 0;
        boolean done = false;
        ArrayList<Double> dftOutputs = new ArrayList<Double>();
        ArrayList<Double> soundRMS = new ArrayList<Double>();
        while(!done) {
            if(finalEndIndex != audio.length - 1) {
//    			System.out.printf("Array Length: %d     ", audio.length);
//    			System.out.printf("Final End Index: %d%n", finalEndIndex);
                audio = Arrays.copyOfRange(audio, finalEndIndex, audio.length);
                short[] newCut = getSoundRMS(audio);
                if(ArrayFunctions.rmsArray(bestCut) < ArrayFunctions.rmsArray(newCut)) {
                    bestCut = newCut;
                }
                soundRMS.add(ArrayFunctions.rmsArray(newCut));
                double[] timePoints = new double[newCut.length];
                for (int i = 0; i < timePoints.length; i++) {
                    timePoints[i] = (double) i / 44100;
                }
                startIndex += finalStartIndex;
                double peak = findMaxDFT(dft(timePoints, newCut));
                System.out.printf("Start Index: %d     peak: %.2f%n", startIndex, peak);
                dftOutputs.add(peak);
//          	  	System.out.printf("End Index: %d%n", finalEndIndex);
            } else {
                done = true;
            }
            startIndex += finalEndIndex - finalStartIndex;
            loops++;
        }
        double[] normalizedWeights = ArrayFunctions.normalize(soundRMS);
        double[] toFile = new double[dftOutputs.size()];
        double[] logs = new double[toFile.length];
        for(int i = 0; i < dftOutputs.size(); i++) {
            toFile[i] = dftOutputs.get(i);
            logs[i] = Math.log10(toFile[i]);
        }
//    	System.out.printf("Median: %.2f%n", getMedian(toFile));
//    	System.out.printf("IQR: %.2f%n", getIQR(toFile));
//    	while(stdDev(toFile) > 50) {
//    		double[] oldFile = toFile;
//    		toFile = noPeaks(toFile);
//    		if(toFile.equals(oldFile)) {
//    			break;
//    		}
//    	}

        double[] filtered = binFilterWithWeights(toFile, logs, normalizedWeights);
        double medianFiltered = getMedian(filtered);
        System.out.printf("Filtered Median: %.2f%n", medianFiltered);
//    	Logging.createLogFile("Logs");
//    	Logging.writeToFile(filtered);
        return bestCut;
    }

    private static double[] noPeaks(double[] data) {
        System.out.printf("No Peaks Param Std Dev: %.2f  Array Size: %d%n", ArrayFunctions.stdDev(data), data.length);
        double stdDev = ArrayFunctions.stdDev(data);

        double[] noPeaks = new double[data.length];
        double prevData = data[0];
        int index = 0;
        for(int i = 1; i < data.length; i ++) {
            if(Math.abs(prevData - data[i]) < stdDev) {
                noPeaks[index] = data[i];
                index++;
            }
            prevData = data[i];
        }
        if(index == 0) {
            return data;
        }
        return Arrays.copyOfRange(noPeaks, 0, index);
    }

    private static double[] binFilter(double[] data, double[] logs) {
        ArrayList<Bin> bins = new ArrayList<Bin>();
        double firstLog = (Math.abs(logs[1] + logs[0])/2);
        double firstFrequency = (Math.abs(data[1] - data[0])/2);
        bins.add(new Bin(firstLog, firstFrequency));
        for (int i = 0; i < logs.length; i ++) {
            boolean added = false;
            for(int j = 0; j < bins.size(); j++) {
                if(Math.abs(bins.get(j).getLogFrequency() - logs[i]) < 0.1) {
                    bins.get(j).addFrequency(data[i]);
                    added = true;
                    break;
                }
            }
            if(!added) {
                bins.add(new Bin(logs[i], data[i]));
            }
        }
        Bin maxBin = bins.get(0);
        for(int i = 1; i < bins.size(); i ++) {
            if(bins.get(i).getLogFrequency() != Double.NEGATIVE_INFINITY) {
                System.out.printf("IQR: %.5f  %s",getLogIQR(bins.get(i).returnArray()), bins.get(i));
//    			System.out.print(bins.get(i));
                if(maxBin.getContentLength() < bins.get(i).getContentLength()) {
                    maxBin = bins.get(i);
                }
            }
        }

//    	Logging.createLogFile("BinFilterOutput");
//    	Logging.writeToFile(bins);
        System.out.println(maxBin);
        return maxBin.returnArray();
    }

    private static double[] binFilterWithWeights(double[] data, double[] logs, double[] weights) {
        ArrayList<Bin> bins = new ArrayList<Bin>();
        double firstLog = Math.abs(logs[1] + logs[0])/2;
        double firstFrequency =  (Math.abs(data[1] - data[0])/2);
        bins.add(new Bin(firstLog, firstFrequency, 0.0));
        for (int i = 0; i < logs.length; i ++) {
            boolean added = false;
            for(int j = 0; j < bins.size(); j++) {
                if(Math.abs(bins.get(j).getLogFrequency() - logs[i]) < 0.1) {
                    bins.get(j).addFrequency(data[i], weights[i]);
                    added = true;
                    break;
                }
            }
            if(!added) {
                bins.add(new Bin(logs[i], data[i], weights[i]));
            }
        }
        Bin maxBin = bins.get(0);
        for(int i = 1; i < bins.size(); i ++) {
            if(bins.get(i).getLogFrequency() != Double.NEGATIVE_INFINITY) {
                System.out.printf("WA: %.2f  CN: %s  SW: %.2f  %s", bins.get(i).weightedAverage(), closestNoteFrequency(bins.get(i).weightedAverage()),
                        bins.get(i).sumOfWeights(), bins.get(i));
//    			System.out.print(bins.get(i));
                if(maxBin.getContentLength() < bins.get(i).getContentLength()) {
                    maxBin = bins.get(i);
                }
            }
        }

//    	Logging.createLogFile("BinFilterOutput");
//    	Logging.writeToFile(bins);
        System.out.println(maxBin);
        return maxBin.returnArray();
    }

    private static double getMedian(double[] data) {
        Arrays.sort(data);
        if (data.length%2==0) {
//    		System.out.println("Even");
            return (data[data.length/2] + data[(data.length/2) - 1])/2;
        }else {
//    		System.out.println("Odd");
            return data[(data.length - 1)/2];
        }
    }

    private static double getIQR(double[] data) {
        Arrays.sort(data);
        if(data.length == 1) {
            return 0;
        }
        if((data.length) % 2 == 0) {
//    		System.out.println("Even");
            double q1 = getMedian(Arrays.copyOfRange(data, 0, data.length/2));
            double q3 = getMedian(Arrays.copyOfRange(data, data.length/2, data.length));

//    		System.out.printf("Q1: %.2f  Q3: %.2f%n", q1, q3);
            return q3 - q1;
        } else {
//    		System.out.println("Odd");
            double q1 = getMedian(Arrays.copyOfRange(data, 0, data.length/2));
            double q3 = getMedian(Arrays.copyOfRange(data, data.length/2 + 1, data.length));
            System.out.println(q3);
//    		System.out.printf("Q1: %.2f  Q3: %.2f%n", q1, q3);
            return q3 - q1;
        }
    }

    private static double getLogIQR(double[] data) {
        Arrays.sort(data);
        double[] logs = new double[data.length];
        for(int i = 0; i < data.length; i ++) {
            logs[i] = Math.log10(data[i]);
        }
        if(data.length == 1) {
            return 0;
        }
        if((data.length) % 2 == 0) {
//       	System.out.println("Even");
            double q1 = getMedian(Arrays.copyOfRange(logs, 0, data.length/2));
            double q3 = getMedian(Arrays.copyOfRange(logs, data.length/2, data.length));

//        	System.out.printf("Q1: %.2f  Q3: %.2f%n", q1, q3);
            return q3 - q1;
        } else {
//        	System.out.println("Odd");
            double q1 = getMedian(Arrays.copyOfRange(logs, 0, data.length/2));
            double q3 = getMedian(Arrays.copyOfRange(logs, data.length/2 + 1, data.length));
            System.out.println(q3);
//        	System.out.printf("Q1: %.2f  Q3: %.2f%n", q1, q3);
            return q3 - q1;
        }
    }

}
