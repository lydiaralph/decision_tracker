package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lydiaralph.decisiontracker.R;

public class SuccessActivity extends MenuBasedActivity {

    private static final String LOG = SuccessActivity.class.getSimpleName();
    private View finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_success);

        finishButton = findViewById(R.id.button_finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.setAction(ViewDecisionsCategoryActivity.VIEW);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
                finish();
            }
        });
    }

}
