package com.lydiaralph.decisiontracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lydiaralph.decisiontracker.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.ralph.lydia.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
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
