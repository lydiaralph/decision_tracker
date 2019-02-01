package com.lydiaralph.decisiontracker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView greetingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetingView = findViewById(R.id.greeting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_bye){
            greetingView.setText(R.string.bye);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void displayResults(View view) {
        Intent intent = new Intent(this, ViewDecisionsActivity.class);
        startActivity(intent);
    }
}
