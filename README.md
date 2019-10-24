# Watermelons
Trying to see whether the sound a watermelon makes when knocked on is related to the quality of said watermelon using Fourier Transforms XD


## Is the sound a watermelon makes when tapped on an indicator for its quality?
People on blogs swear by their own methods for choosing watermelons, but often accounts conflict from person to person. Some say that it should sound hollow; others say it shouldn't. Who's correct?

## Methodology
Using a short audio recording of a knock on a watermelon, the corresponding note will be returned by the discrete fourier transform function. 

Following is the function to return the frequencies in the sample and the associated counts.
```
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
    
```
Before this, the sound sample is broken down into smaller samples concentrated around the knocking sound we want to sample.
```
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
```

## So ... what now?
Does the sound seem to match the general quality of the watermelon? Well, the first problem is that watermelons don't have a uniform density and circular shape, so tapping in different areas produces different notes. So ... we'll maybe revist this later.
