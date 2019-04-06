package com.lydiaralph.decisiontracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class MenuBasedActivity extends AppCompatActivity {

    protected View returnToMainMenuButton;
    protected View viewResultsButton;
    protected View configureNewDecisionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                displayResults(v);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.view_results) {
            displayResults(viewResultsButton);
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
        Intent intent = new Intent(this, ViewDecisionsActivity.class);
        startActivity(intent);
    }

    public void configureNewDecision(View view) {
        Intent intent = new Intent(this, ConfigureNewDecisionActivity.class);
        startActivity(intent);
    }
}
