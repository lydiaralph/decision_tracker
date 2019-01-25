package com.lydiaralph.decisiontracker;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_DECISION_ACTIVITY_REQUEST_CODE = 1;

    private DecisionViewModel decisionViewModel;

    public static final String EXTRA_MESSAGE = "com.ralph.lydia.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void updateConfiguration(View view) {
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
