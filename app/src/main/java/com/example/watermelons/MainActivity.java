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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private static String lastDate = null;

    private RecordButton recordButton = null;
    private MediaRecorder recorder = null;
    private AudioRecord audioRecorder = null;

    private int audioSource = MediaRecorder.AudioSource.MIC;
    private int samplingRate = 48000; // Hz
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSize = 192; //AudioRecord.getMinBufferSize(samplingRate, channelConfig, audioFormat);
    private int sampleNumBits = 16;
    private int numChannels = 1;

    private double recordTime = 1;
    private long startTime = 0;

    private int currentIndex = 0;

    private double[] dataArray = null;
    private double[] timePoints = null;

    private boolean isRecording = false;

    private PlayButton playButton = null;
    private MediaPlayer player = null;

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
        audioRecorder.startRecording();
        isRecording = true;
        startTime = System.currentTimeMillis();
    }

    private void stopRecording() {
        audioRecorder.stop();
        audioRecorder = null;
        isRecording = false;
        currentIndex = 0;
        for(int i = 0; i < dataArray.length; i ++) {
            Log.d("Printing Data", "Data Array -> " + dataArray[i]);
        }
        Log.d("While Reocrding", "Before DFT");
        dft(timePoints, dataArray);
    }

    private int read() {
        byte[] data = new byte[8];
        int readBytes = audioRecorder.read(data, 0, bufferSize);
        for(int i = 0; i < data.length; i ++) {
            Log.d("Reading Data", "Bytes -> " + data);
        }
        return readBytes;
    }

    private void dft(double[] inreal, double[] inimag) {
        int n = inreal.length;

        double[] outreal = new double[n];
        double[] outimag = new double[n];

//        Log.d("Inside DFT", "Before Loop: " + n);
        for(int k = 0; k < n; k++) {
            double sumreal = 0;
            double sumimag = 0;

            for (int t = 0; t < n; t++) {
                //Log.d("Inside DFT", "First For Loop: " + t);
                double angle = 2 * Math.PI * t * k / n;
                sumreal += inreal[t] * Math.cos(angle) + inimag[t] * Math.sin(angle);
                sumimag += -inreal[t] * Math.sin(angle) + inimag[t] * Math.cos(angle);
            }
            outreal[k] = sumreal;
            outimag[k] = sumimag;
//            Log.d("Inside DFT", "Firsrst For Loop: " + k + "   e   " + sumreal + "   " + sumimag);

        }
        Log.d("Inside DFT", "Finished");
    }


    class RecordButton extends AppCompatButton {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if(mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("App Up", "Staring");
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        Log.d("Initializing", "MinBufferSize -> " + bufferSize);
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
        playButton = new PlayButton(this);
        ll.addView(playButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        setContentView(ll);

        dataArray = new double[samplingRate * (int) recordTime];
        timePoints = new double[dataArray.length];
        for(int i = 0; i < timePoints.length; i++) {
            timePoints[i] = (double) i/ samplingRate;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    while (isRecording) {
                        Log.d("Thread", "isRecording");
                        try {
                            if (System.currentTimeMillis() < startTime + recordTime * 1000) {
                                dataArray[currentIndex] = read();
                                currentIndex++;
                            } else {
                                stopRecording();
                                Log.d("While Reocrding", "Out of Time");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            stopRecording();
                            Log.d("While Reocrding", "Array Index Out of Bounds");
                        }
                    }
                }
            }
        }).start();
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
}
