package com.lydiaralph.decisiontracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
        final Spinner trackerPeriodView = findViewById(R.id.input_tracker_period);

        Integer[] trackerPeriodItems = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, trackerPeriodItems);
        trackerPeriodView.setAdapter(adapter);

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
                    Integer trackerPeriod = Integer.parseInt(trackerPeriodView.getSelectedItem().toString());

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

    private String getTrackerPeriodType(RadioGroup radioTrackerPeriodType) {
        int selectedId = radioTrackerPeriodType.getCheckedRadioButtonId();
        RadioButton radioTrackerPeriodTypeButton = findViewById(selectedId);
        return radioTrackerPeriodTypeButton.getText().toString();
    }
}
