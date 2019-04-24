package com.lydiaralph.decisiontracker.activities;

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
import android.widget.Toast;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.DateUtils;
import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionInsert;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import java.util.Arrays;

import androidx.lifecycle.ViewModelProviders;

import static com.lydiaralph.decisiontracker.activities.ViewDecisionsCategoryActivity.VIEW;

public class ConfigureNewDecisionActivity extends MenuBasedActivity {

    private static final String LOG = ConfigureNewDecisionActivity.class.getSimpleName();

    private DecisionViewModel decisionViewModel;
    private View persistNewDecisionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_configure_new_decision);
        setReturnToMainMenuButton();
        decisionViewModel = ViewModelProviders.of(this).get(DecisionViewModel.class);

        final EditText decisionTextView = findViewById(R.id.input_decision_text);
        final Spinner trackerPeriodView = findViewById(R.id.input_tracker_period);
        final EditText option1View = findViewById(R.id.input_option_1);
        final EditText option2View = findViewById(R.id.input_option_2);

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
                resultIntent.setAction(VIEW);

                if (TextUtils.isEmpty(decisionTextView.getText())) {
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                } else {
                    String decisionText = decisionTextView.getText().toString();
                    String option1Text = option1View.getText().toString();
                    String option2Text = option2View.getText().toString();
                    String trackerPeriodType = getTrackerPeriodType(radioTrackerPeriodType);
                    Integer trackerPeriod = Integer.parseInt(trackerPeriodView.getSelectedItem().toString());

                    Log.i(LOG, "Input decision text: " + decisionText);

                    if (decisionText != null) {
                        if(option1Text.isEmpty() || option2Text.isEmpty()){
                            Toast.makeText(
                                    getApplicationContext(),
                                    getString(R.string.configure_at_least_two_options),
                                    Toast.LENGTH_LONG).show();
                        }
                        DateUtils dateUtils = DateUtilsImpl.getInstance();
                        Decision decision = new Decision(dateUtils, decisionText);
                        decision.setDates(dateUtils, trackerPeriodType, trackerPeriod);

                        DecisionInsert toPersist = new DecisionInsert(decision, Arrays.asList(option1Text, option2Text));
                        decisionViewModel.insert(toPersist);
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                R.string.empty_not_saved,
                                Toast.LENGTH_LONG).show();
                    }
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
