package com.lydiaralph.decisiontracker;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.ralph.lydia.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void updateConfiguration(View view) {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }


//    void displayResults(View view) {
//        Intent intent = new Intent(this, ConfigurationActivity.class);
//        startActivity(intent);
//    }
//
//    void answerQuestions(View view) {
//        Intent intent = new Intent(this, ConfigurationActivity.class);
//        startActivity(intent);
//    }
}
