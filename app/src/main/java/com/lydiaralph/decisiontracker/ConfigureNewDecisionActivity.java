package com.lydiaralph.decisiontracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ConfigureNewDecisionActivity extends MenuBasedActivity {
    public static final String PERSIST = "persist";
    private static final String LOG = ConfigureNewDecisionActivity.class.getSimpleName();

    public static final String INPUT_DECISION_TEXT = "InputDecisionText";
    public static final String INPUT_TRACKER_PERIOD = "InputTrackerPeriod";
    public static final String INPUT_TRACKER_PERIOD_TYPE = "InputTrackerPeriodType";

    private View persistNewDecisionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_configure_new_decision);
        setReturnToMainMenuButton();

        final EditText decisionTextView = findViewById(R.id.input_decision_text);
        final EditText trackerPeriodView = findViewById(R.id.input_tracker_period);
        final RadioGroup radioTrackerPeriodType = findViewById(R.id.radio_tracker_period_type);

        persistNewDecisionButton = findViewById(R.id.button_submit_new_decision);
        persistNewDecisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(ConfigureNewDecisionActivity.this, ViewDecisionsCategoryActivity.class);
                resultIntent.setAction(PERSIST);

                if (TextUtils.isEmpty(decisionTextView.getText())) {
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                } else {
                    String decisionText = decisionTextView.getText().toString();
                    String trackerPeriodType = getTrackerPeriodType(radioTrackerPeriodType);
                    Integer trackerPeriod = getTrackerPeriodUnits(trackerPeriodView);

                    Log.i(LOG, "Input decision text: " + decisionText);
                    resultIntent.putExtra(INPUT_DECISION_TEXT, decisionText);
                    resultIntent.putExtra(INPUT_TRACKER_PERIOD, trackerPeriod);
                    resultIntent.putExtra(INPUT_TRACKER_PERIOD_TYPE, trackerPeriodType);
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                startActivity(resultIntent);
                finish();
            }
        });
    }

    private Integer getTrackerPeriodUnits(EditText trackerPeriodView) {
        Integer trackerPeriod = 0;
        if (trackerPeriodView.getText() != null && !trackerPeriodView.getText().toString().isEmpty()) {
            trackerPeriod = Integer.valueOf(trackerPeriodView.getText().toString());
        }
        return trackerPeriod;
    }

    private String getTrackerPeriodType(RadioGroup radioTrackerPeriodType) {
        int selectedId = radioTrackerPeriodType.getCheckedRadioButtonId();
        RadioButton radioTrackerPeriodTypeButton = findViewById(selectedId);
        return radioTrackerPeriodTypeButton.getText().toString();
    }
}
