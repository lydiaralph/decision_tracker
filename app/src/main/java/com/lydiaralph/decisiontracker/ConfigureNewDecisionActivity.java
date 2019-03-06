package com.lydiaralph.decisiontracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigureNewDecisionActivity extends MenuBasedActivity {
    private static final String LOG = ConfigureNewDecisionActivity.class.getSimpleName();

    public static final String INPUT_DECISION_TEXT = "InputDecisionText";

    private View persistNewDecisionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_new_decision);
        setReturnToMainMenuButton();

        final EditText decisionTextView = findViewById(R.id.input_decision_text);
        persistNewDecisionButton = findViewById(R.id.button_submit_new_decision);
        persistNewDecisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(decisionTextView.getText())) {
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                } else {
                    String decisionText = decisionTextView.getText().toString();
                    Log.i(LOG, "Input decision text: " + decisionText);
                    resultIntent.putExtra(INPUT_DECISION_TEXT, decisionText);
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                finish();
            }
        });
    }

}
