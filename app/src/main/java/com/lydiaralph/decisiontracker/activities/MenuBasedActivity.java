package com.lydiaralph.decisiontracker.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lydiaralph.decisiontracker.R;

public abstract class MenuBasedActivity extends AppCompatActivity {

    private static final String LOG = MenuBasedActivity.class.getSimpleName();

    protected View returnToMainMenuButton;
    protected View viewResultsButton;
    protected View configureNewDecisionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
    }

    protected void setReturnToMainMenuButton() {
        returnToMainMenuButton = findViewById(R.id.button_return_to_main_menu);
        returnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainMenu(v);
            }
        });
    }

    protected void setViewResultsButton() {
        viewResultsButton = findViewById(R.id.button_view_results);
        viewResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewDecisionsCategoryActivity.class);
                intent.setAction(ViewDecisionsCategoryActivity.VIEW);
                startActivity(intent);
            }
        });
    }

    protected void setConfigureNewDecisionButton() {
        configureNewDecisionButton = findViewById(R.id.button_configure);
        configureNewDecisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureNewDecision(v);
            }
        });
    }

    protected void setVoteOnDecisionButton() {
        viewResultsButton = findViewById(R.id.button_vote);
        viewResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewDecisionsCategoryActivity.class);
                intent.setAction(ViewDecisionsCategoryActivity.VOTE);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.view_results) {
            Intent intent = new Intent(getApplicationContext(), ViewDecisionsCategoryActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.configure_new_decision) {
            configureNewDecision(configureNewDecisionButton);
        } else if (item.getItemId() == R.id.return_to_main_menu) {
            returnToMainMenu(returnToMainMenuButton);
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void displayResults(View view) {
        Intent intent = new Intent(this, ViewDecisionsCategoryActivity.class);
        startActivity(intent);
    }

    public void configureNewDecision(View view) {
        Intent intent = new Intent(this, ConfigureNewDecisionActivity.class);
        startActivity(intent);
    }
}
