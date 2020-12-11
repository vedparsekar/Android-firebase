package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Audio extends AppCompatActivity {

    private Button startbtn, stopbtn, playbtn, stopplay;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
        MediaRecorder recorder;
        File audiofile = null;
        static final String TAG = "MediaRecording";
        Button startButton,stopButton;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_audio);
            startButton = (Button) findViewById(R.id.button1);
            stopButton = (Button) findViewById(R.id.button2);
        }

        public void startRecording(View view) throws IOException {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            //Creating file
            File dir = Environment.getExternalStorageDirectory();
            try {
                audiofile = File.createTempFile("sound", ".3gp", dir);
            } catch (IOException e) {
                Log.e(TAG, "external storage access error");
                e.printStackTrace();
                return;
            }
            //Creating MediaRecorder and specifying audio source, output format, encoder & output format
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(audiofile.getAbsolutePath());
            recorder.prepare();
            recorder.start();
        }

        public void stopRecording(View view) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            //stopping recorder
            recorder.stop();
            recorder.release();
            //after stopping the recorder, create the sound file and add it to media library.
            addRecordingToMediaLibrary();
        }

        protected void addRecordingToMediaLibrary() {
            //creating content values of size 4
            ContentValues values = new ContentValues(4);
            long current = System.currentTimeMillis();
            values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
            values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
            values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
            values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());

            //creating content resolver and storing it in the external content uri
            ContentResolver contentResolver = getContentResolver();
            Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Uri newUri = contentResolver.insert(base, values);

            //sending broadcast message to scan the media file so that it can be available
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
            Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
        }
    }