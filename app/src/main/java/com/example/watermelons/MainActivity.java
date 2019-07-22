package com.example.watermelons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private RecordButton recordButton = null;
    private MediaRecorder recorder = null;
    private AudioRecord audioRecorder = null;
    private TextView textView = null;

    private int audioSource = MediaRecorder.AudioSource.MIC;
    public static final int samplingRate = 44100; // Hz
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSize = AudioRecord.getMinBufferSize(samplingRate, channelConfig, audioFormat);


    private int currentIndex = 0;


    private double[] timePoints = null;
    private short[] fullAudioData = null;
    private short[] highlight = null;
    private double[][] output = null;

    private double peak;

    private boolean isRecording = false;

    private MediaPlayer player = null;

    private static PrintWriter writer;

    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(!permissionToRecordAccepted) finish();
    }

    private void onRecord(boolean start) {
        if(start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if(start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        audioRecorder = new AudioRecord(audioSource, samplingRate, channelConfig, audioFormat, bufferSize);
        audioRecorder.startRecording();
        isRecording = true;
        fullAudioData = null;
//        recording();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRecording) {
                    Log.d("Thread", "isRecording");
                    try {
                        final short[] pktBuf = new short[bufferSize];
//                            int recordStatus = audioRecorder.getState();
//                            Log.d("While Recording", "Current State: " + recordStatus);
                        if(audioRecorder != null) {
                            readFully(pktBuf, 0, bufferSize);
                            currentIndex++;
                            Log.d("While Reocrding", "index " + currentIndex + "   " + bufferSize);

                            Log.d("While Recording", "RMS Recording Value: " + SoundAnalysis.rmsArray(pktBuf));
                            if(isRecording) {
                                if (fullAudioData == null) {
                                    fullAudioData = pktBuf;
                                } else {
                                    fullAudioData = addArrays(fullAudioData, pktBuf);
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Log.d("While Reocrding", "Array Index Out of Bounds");
                    }
                }
            }
        }).start();
    }

    /**
     * Called when AudioRecord stops recording
     * Frees AudioRecord resources
     * Runs DFT
     * Writes Data to File
     */
    private void stopRecording() {
        if(audioRecorder != null && fullAudioData != null) {
            audioRecorder.stop();
            audioRecorder = null;
            isRecording = false;

            new DFTBackgroundProcessing().execute();



//
//            createLogFile();
//            writeToFile();
        }
    }

    /**
     * Makes sure that the entire read array is filled
     * @param data the audio data
     * @param off offset of read
     * @param length length of buffer array
     */
    private void readFully(short[] data, int off, int length) {
        int read;
        while (audioRecorder != null && length > 0) {
            read = audioRecorder.read(data, off, length);
//            Log.d("While Recording", "Current Read Status: " + read);
            length -= 1;
            off += read;
        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("App Up", "Staring");
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        audioRecorder = new AudioRecord(audioSource, samplingRate, channelConfig, audioFormat, bufferSize);
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/watermelons";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        LinearLayout ll = new LinearLayout(this);

        recordButton = new RecordButton(this);
        ll.addView(recordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        textView = new TextView(this);
        ll.addView(textView,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0));
        setContentView(ll);


    }

    @Override
    public void onStop() {
        super.onStop();
        if(recorder != null) {
            recorder.release();
            recorder = null;
        }

        if(player != null) {
            player.release();
            player = null;
        }
    }

    public short[] addArrays(short[] a, short[] b) {
        short[] sum = new short[a.length + b.length];
        for (int i = 0; i < a.length; i ++) {
            sum[i] = a[i];
        }
        for(int i = 0; i < b.length; i ++) {
            sum[i + a.length] = b[i];
        }
        return sum;
    }

    private void writeToFile() {
        writer.printf("Writing Full Audio Data %n");
        for(int i = 0; i < fullAudioData.length; i++) {
            writer.printf("%d%n", fullAudioData[i]);
        }

//        writer.printf("Writing Full Audio Data %n");
//        for(int i = 0; i < highlight.length; i++) {
//            writer.printf("%.5f, %d%n", timePoints[i], highlight[i]);
//        }

//        writer.printf("Writing DFT Output%n");
//        for (int i = 0; i < output.length; i ++) {
//            writer.printf("%.5f, %.5f%n", output[i][0], output[i][1]);
//        }
        writer.close();
    }

    private void createLogFile() {
        try {
            String fileLocation = getExternalCacheDir().getAbsolutePath() + "/watermelons" + new SimpleDateFormat("MMddhhmm'.csv'").format(new Date());
            File file = new File(fileLocation);
            writer = new PrintWriter(fileLocation, "UTF-8");
            Log.d("Creating Log File", "Log File Name: " + fileLocation);
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            Log.d("Creating Log File", "We Got Problems");
        }
    }



    private double[] localSpikes(double[][] data) {
        return null;
    }

    class RecordButton extends AppCompatButton {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                if(!isRecording) {
                    onRecord(true);
                    setText("Stop Record");
                } else {
                    Log.d("Button Press", "Calling stop record");
                    setText("Start Recording");
                    stopRecording();
                }

            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends AppCompatButton {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(mStartPlaying);
                if(mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    private class DFTBackgroundProcessing extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            String text = String.format("Full Audio Array Length: %d%n", fullAudioData.length);
            textView.setText(text);
            recordButton.setText("Processing");
        }


        @Override
        protected Void doInBackground(Void... params) {
//            Log.d("Stop Recording", "Array Length: " + fullAudioData.length);
            highlight = SoundAnalysis.getHighlightRMS(fullAudioData);

            timePoints = new double[highlight.length];
            for (int i = 0; i < timePoints.length; i++) {
                timePoints[i] = (double) i / samplingRate;
            }
            Log.d("While Reocrding", "Before DFT");

            output = SoundAnalysis.dft(timePoints, highlight);

            Log.d("While Reocrding", "After DFT");
            peak = SoundAnalysis.findMax(output);
            return null;
        }

        @Override
        protected void onPostExecute(Void param){
            Log.d("Stop Recording", "Max DFT Value: " + peak);
            Log.d("Stop Recording", "Note: " + SoundAnalysis.closestNoteFrequency(peak));
            String text = String.format("DFT Output Peak: %.2f  Closest Note: %s", peak, SoundAnalysis.closestNoteFrequency(peak));
            textView.setText(text);
            recordButton.setText("Start Recording");
        }




    }



}
