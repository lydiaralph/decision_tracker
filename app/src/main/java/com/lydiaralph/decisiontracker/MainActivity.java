package com.lydiaralph.decisiontracker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    private View viewResultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewResultsButton = findViewById(R.id.button_view_results);
        viewResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayResults(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.view_results){
            displayResults(viewResultsButton);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayResults(View view) {
        Intent intent = new Intent(this, ViewDecisionsActivity.class);
        startActivity(intent);
    }
}
