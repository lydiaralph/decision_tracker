package com.lydiaralph.decisiontracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.DateUtils;
import com.lydiaralph.decisiontracker.database.entity.DateUtilsImpl;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.entity.DecisionInsert;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

public class ConfigureNewDecisionActivity extends MenuBasedActivity {

    private static final String LOG = ConfigureNewDecisionActivity.class.getSimpleName();

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private DecisionViewModel decisionViewModel;
    private View persistNewDecisionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_2_configure_new_decision);
        setReturnToMainMenuButton();
        decisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DecisionViewModel.class);

        final EditText decisionTextView = findViewById(R.id.input_decision_text);
        final EditText option1View = findViewById(R.id.input_option_1);
        final EditText option2View = findViewById(R.id.input_option_2);

        DatePicker picker = findViewById(R.id.datePicker1);

        persistNewDecisionButton = findViewById(R.id.button_submit_new_decision);
        persistNewDecisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(ConfigureNewDecisionActivity.this, MainActivity.class);

                    Optional<DecisionInsert> toPersist = createObjectToPersist(decisionTextView, option1View, option2View, picker);

                    // Only finish the activity if everything is fine
                    if(toPersist.isPresent()){
                        decisionViewModel.insert(toPersist.get());
                        startActivity(resultIntent);
                        finish();
                    }
//                }
            }
        });
    }

    @NotNull
    private Optional<DecisionInsert> createObjectToPersist(EditText decisionTextView, EditText option1View, EditText option2View, DatePicker picker) {
        Optional<String> decisionText = getDecisionText(decisionTextView);

        if(!decisionText.isPresent()){
            return Optional.empty();
        }

        Optional<LocalDate> endDate = getEndDate(picker);
        if(!endDate.isPresent()){
            return Optional.empty();
        }

        Optional<List<String>> optionTextList = getOptionTexts(Arrays.asList(option1View, option2View));
        if(!optionTextList.isPresent()){
            return Optional.empty();
        }

        DateUtils dateUtils = DateUtilsImpl.getInstance();
        Decision decision = new Decision(dateUtils, decisionText.get(), endDate.get());

        return Optional.of(new DecisionInsert(decision, optionTextList.get()));
    }

    private Optional<String> getDecisionText(EditText decisionTextView) {
        if (decisionTextView.getText() == null || decisionTextView.getText().toString().isEmpty()) {
            decisionTextView.setError(getString(R.string.empty_not_saved));
            decisionTextView.requestFocus();
            return Optional.empty();
        }
        return Optional.of(decisionTextView.getText().toString());
    }

    private Optional<LocalDate> getEndDate(DatePicker picker) {
        LocalDate endDate = LocalDate.of(picker.getYear(), picker.getMonth() + 1, picker.getDayOfMonth());
        if (endDate.isBefore(LocalDate.now())) {

            final TextView calendarPrompter = findViewById(R.id.prompt_tracker_period);
            calendarPrompter.requestFocus();
            calendarPrompter.setError(getString(R.string.end_date_should_be_after_today));

            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.end_date_should_be_after_today),
                    Toast.LENGTH_LONG).show();
            return Optional.empty();
        }
        return Optional.of(endDate);
    }

    private Optional<List<String>> getOptionTexts(List<EditText> optionsViewList) {
        List<String> optionTextList = new ArrayList<>();

        for(EditText option : optionsViewList ) {
            if(option.getText() != null && ! option.getText().toString().isEmpty()) {
                optionTextList.add(option.getText().toString());
            }
        }

        if (optionTextList.size() < 2 ) {
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.configure_at_least_two_options),
                        Toast.LENGTH_LONG).show();
                optionsViewList.get(0).requestFocus();
                optionsViewList.get(0).setError(getString(R.string.configure_at_least_two_options));
                return Optional.empty();
        }
        return Optional.of(optionTextList);
    }
}
