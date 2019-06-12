package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModelFactory;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;
import com.lydiaralph.decisiontracker.database.entity.Vote;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.VoteViewModel;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

public class VoteActivity extends MenuBasedActivity {
    public static final String VOTE_ID = "VoteId";
    private static final String LOG = VoteActivity.class.getSimpleName();
    public static final String INPUT_SELECTED_OPTION_ID = "SelectedOptionId";

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private DecisionViewModel decisionViewModel;
    private VoteViewModel voteViewModel;
    private View persistVoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG, "Starting activity");
        setContentView(R.layout.activity_6_vote);
        decisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DecisionViewModel.class);
        voteViewModel = ViewModelProviders.of(this, viewModelFactory).get(VoteViewModel.class);

        Integer selectedDecisionId = (Integer) getIntent().getExtras().get(ViewDecisionsCategoryActivity.VIEW_DECISION_ID);

        LinearLayout myRoot = findViewById(R.id.display_options);
        RadioGroup optionsSelection = new RadioGroup(this);
        optionsSelection.setOrientation(LinearLayout.VERTICAL);

        final Observer<DecisionOptions> decisionObserver = new Observer<DecisionOptions>() {
            @Override
            public void onChanged(@Nullable final DecisionOptions decision) {
                if(decision.getOptionsList().isEmpty()){
                    Intent resultIntent = new Intent(VoteActivity.this, ViewDecisionsCategoryActivity.class);
                    resultIntent.setAction(ViewDecisionsCategoryActivity.VIEW);
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.no_options_placeholder,
                            Toast.LENGTH_LONG).show();
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    startActivity(resultIntent);
                    finish();
                }
                TextView decisionTextView = findViewById(R.id.display_decision_text);
                decisionTextView.setText(decision.getDecision().getDecisionText());

//                TextView datesView = findViewById(R.id.display_dates);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
//                datesView.setText(String.format("%s - %s",
//                        decision.getDecision().getStartDate().format(formatter),
//                        decision.getDecision().getEndDate().format(formatter)));

                if(!decision.getOptionsList().isEmpty()){
                    setOptionsSelection(optionsSelection, decision);
                }
                myRoot.removeAllViews();
                myRoot.addView(optionsSelection);
            }
        };

        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);

        persistVoteButton = findViewById(R.id.button_save);
        persistVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedOptionId = optionsSelection.getCheckedRadioButtonId();
                if (selectedOptionId == -1 || selectedOptionId == 999) {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_option_not_saved,
                            Toast.LENGTH_LONG).show();
                    Intent resultIntent = new Intent(VoteActivity.this, ViewDecisionsCategoryActivity.class);
                    resultIntent.setAction(ViewDecisionsCategoryActivity.VIEW);
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    startActivity(resultIntent);
                    finish();
                } else {
                    RadioButton selectedOption = findViewById(selectedOptionId);
                    Log.i(LOG, "Voted for : " + selectedOption.getText().toString());

                    try {
                        Integer voteId = voteViewModel.insert(new Vote(selectedOptionId, LocalDate.now()));

                        Intent resultIntent = new Intent(VoteActivity.this, MoodTrackerActivity.class);
                        resultIntent.putExtra(VOTE_ID, voteId);
                        setResult(Activity.RESULT_OK, resultIntent);
                        startActivity(resultIntent);
                        finish();
                    } catch (ExecutionException | InterruptedException exception){
                        Log.e(LOG, "Could not insert vote into database: " + exception.getCause());
                    }
                }
            }
        });
    }

    private void setOptionsSelection(RadioGroup radioButtonGroup,
                                     final DecisionOptions decision) {
        RadioButton noVoteRadioButton = new RadioButton(this);
        noVoteRadioButton.setText(getString(R.string.no_vote_today));
        noVoteRadioButton.setId(Integer.valueOf(999));
        radioButtonGroup.addView(noVoteRadioButton);

        for(OptionsVotes option : decision.getOptionsList()){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option.getOption().getOptionText());
            radioButton.setId(option.getOption().getId());
            radioButtonGroup.addView(radioButton);
        }
    }

}
