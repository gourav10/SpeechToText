package com.example.speechtotext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    ImageButton imgButton;
    EditText editText;
    RecyclerView listView;
    public static final int AUDIOMETRIC = 1;
    SpeechRecognizer speechRecognizer;
    String s1[],s2[];
    int count =0;
    int imgList[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButton = findViewById(R.id.activityMain_BtnMic);
        editText = findViewById(R.id.actvityMain_EdText);
        listView = findViewById(R.id.activityMain_listView);
        s1 = getResources().getStringArray(R.array.programming_languages);
        s2 = getResources().getStringArray(R.array.programming_description);


        //Check if App has permission to record
        if(isMicrophonePresent()){
            getMicPermission();
        }

        MainListAdapter dummyAdapter = new MainListAdapter(this,s1,s2,imgList);
        listView.setAdapter(dummyAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        final Intent speechRecognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==0){
                    imgButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_24));
                    //start speech recognition
                    speechRecognizer.startListening(speechRecognitionIntent);
                    count =1;
                }
                else{
                    imgButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_off_24));
                    //stop listening
                    speechRecognizer.stopListening();
                    count =0;
                }

            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.v(LOG_TAG, "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.v(LOG_TAG, "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                Log.v(LOG_TAG, "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.v(LOG_TAG, "onEndOfSpeech");
            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                //When speech recognition is stopped
                ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                String validString = "";
                for (String action: Constants.ACCEPTABLE_ACTIONS) {
                    if(data.get(0).contains(action)){
                        validString = data.get(0);
                        break;
                    }
                }
                editText.setText(validString);

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == AUDIOMETRIC){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else{
            return false;
        }
    }

    private void getMicPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},AUDIOMETRIC);
        }
    }
}