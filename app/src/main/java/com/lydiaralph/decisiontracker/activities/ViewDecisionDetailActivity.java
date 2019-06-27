package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.charts.BarChartFragment;
import com.lydiaralph.decisiontracker.charts.LineChartFragment;
import com.lydiaralph.decisiontracker.charts.PieChartFragment;
import com.lydiaralph.decisiontracker.database.entity.DecisionOptions;
import com.lydiaralph.decisiontracker.database.entity.MoodDescriptionWithIntensity;
import com.lydiaralph.decisiontracker.database.entity.OptionsVotes;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModelFactory;
import com.lydiaralph.decisiontracker.database.viewmodel.MoodViewModel;
import com.lydiaralph.decisiontracker.fragments.TerminateDecisionTrackingFragment;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

/**
 * Inspired by https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/main/java/com/xxmassdeveloper/mpchartexample/PieChartActivity.java
 */

public class ViewDecisionDetailActivity extends FragmentActivity implements TerminateDecisionTrackingFragment.Listener {

    private static final String LOG_NAME = ViewDecisionDetailActivity.class.getSimpleName();
    private Spinner chooseChartOptionSpinner;

    private LineChartFragment lineChartFragment;
    private PieChartFragment pieChartFragment;
    private BarChartFragment barChartFragment;

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private DecisionViewModel decisionViewModel;
    private MoodViewModel moodViewModel;

    private List<MoodDescriptionWithIntensity> inputData;
    private DecisionOptions decisionOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG_NAME, "Starting activity");
        setContentView(R.layout.activity_9_view_decision_detail);

        Integer selectedDecisionId = (Integer) getIntent().getExtras().get(ViewDecisionsCategoryActivity.VIEW_DECISION_ID);
        decisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DecisionViewModel.class);
        moodViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoodViewModel.class);

        final Observer<DecisionOptions> decisionObserver = getDecisionOptionsObserver();
        final Observer<List<MoodDescriptionWithIntensity>> moodObserver = getMoodObserver();
        decisionViewModel.getDecisionById(selectedDecisionId).observe(this, decisionObserver);
        moodViewModel.getAllMoodsByDecisionId(selectedDecisionId)
                .observe(ViewDecisionDetailActivity.this, moodObserver);

        if (findViewById(R.id.fragment_container) != null) {
            Log.i(LOG_NAME, "Creating fragments");

            if (savedInstanceState != null) {
                return;
            }

            lineChartFragment = new LineChartFragment();
            lineChartFragment.setArguments(getIntent().getExtras());

            pieChartFragment = new PieChartFragment();
            barChartFragment = new BarChartFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, lineChartFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, pieChartFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, barChartFragment)
                    .commit();

            addItemsOnSpinner();
        }
    }

    public void addItemsOnSpinner() {
        chooseChartOptionSpinner = findViewById(R.id.spinner1);

        // TODO: Replace these with actual IDs
        List<String> list = new ArrayList<>();
        list.add("Line chart");
        list.add("Pie chart");
        list.add("Bar chart");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseChartOptionSpinner.setAdapter(dataAdapter);

        chooseChartOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {

                String chosenItem = parent.getItemAtPosition(position).toString();

                switch (chosenItem){
                    case "Line chart":
                        lineChartFragment.displayData(inputData);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, lineChartFragment)
                                .commit();
                        break;
                    case "Bar chart":
                        barChartFragment.displayData(decisionOptions);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, barChartFragment)
                                .commit();
                        break;
                    case "Pie chart":
                        pieChartFragment.displayData(decisionOptions);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, pieChartFragment)
                                .commit();
                        break;
                    default:
                        Log.e(LOG_NAME, "Unrecognised chart option " + chosenItem);
                        getSupportFragmentManager().beginTransaction()
                                .remove(lineChartFragment)
                                .remove(pieChartFragment)
                                .remove(barChartFragment)
                                .commit();

                    Toast.makeText(parent.getContext(), "Unrecognised chart option " + chosenItem,
                            Toast.LENGTH_SHORT).show();

                }

                Toast.makeText(parent.getContext(),
                        "Chart on display : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }

        });
    }

    @Override
    public void terminateDecisionTrackingEarly(DialogFragment dialog, int decisionId) {
        // Update decision expiry date to today to stop tracking
        decisionViewModel.updateEndDate(decisionId, LocalDate.now());
        // View results
        Intent intent = getIntent();
        intent.putExtra(ViewDecisionsCategoryActivity.VIEW_DECISION_ID, decisionId);
        finish();
        this.startActivity(intent);
    }

    @Override
    public void keepTrackingDecision(DialogFragment dialog) {
        // Go back to list of decisions
        Intent resultIntent = new Intent(ViewDecisionDetailActivity.this, ViewDecisionsCategoryActivity.class);
        resultIntent.setAction(ViewDecisionsCategoryActivity.VIEW);
        setResult(Activity.RESULT_CANCELED, resultIntent);
        startActivity(resultIntent);
        finish();
    }

    @NotNull
    private Observer<DecisionOptions> getDecisionOptionsObserver() {
        TextView editorialTextView = findViewById(R.id.decision_text);

        return new Observer<DecisionOptions>() {
            @Override
            public void onChanged(@Nullable final DecisionOptions decision) {
                if (decision.getDecision().getEndDate().compareTo(LocalDate.now()) > 0) {

                    TerminateDecisionTrackingFragment terminateDecisionTrackingFragment = TerminateDecisionTrackingFragment.newInstance(decision.getDecision().getId());
                    terminateDecisionTrackingFragment.show(getSupportFragmentManager(), "terminateDecision");
                } else {
                    TextView title = findViewById(R.id.page_title);
                    title.setText(getString(R.string.you_decided));

                    TextView datesView = findViewById(R.id.display_dates);
                    datesView.setText(getFormattedDateString(decision));

                    if (!decision.getOptionsList().isEmpty()) {
                        // More efficient with lambda? But bootstrap error
                        boolean hasVotes = false;
                        for (OptionsVotes optionsVotes : decision.getOptionsList()) {
                            if (optionsVotes.countVotes() > 0) {
                                hasVotes = true;
                            }
                        }
                        if (!hasVotes) {
                            editorialTextView.setText(getString(R.string.no_votes_for_this_decision));
                        } else {
                            setDecisionOptions(decision);
                            editorialTextView.setText(decision.getDecision().getDecisionText());
                        }
                    }

                    else {
                        editorialTextView.setText(R.string.no_options_placeholder);
                    }
                }
            }

        };
    }

    @NotNull
    private Observer<List<MoodDescriptionWithIntensity>> getMoodObserver() {
        return new Observer<List<MoodDescriptionWithIntensity>>() {
            @Override
            public void onChanged(List<MoodDescriptionWithIntensity> moodDescriptionWithIntensities) {
                setChartDataToDisplay(moodDescriptionWithIntensities);
            }
        };
    }

    private void setDecisionOptions(DecisionOptions decisionOptions) {
        this.decisionOptions = decisionOptions;
    }

    private void setChartDataToDisplay(List<MoodDescriptionWithIntensity> data) {
        this.inputData = data;
    }

//    private void displayMoodsAsSimpleList(List<MoodDescriptionWithIntensity> moodDescriptionWithIntensities) {
//        List<String> arrayOfMoods = SimpleListDisplay.setData(moodDescriptionWithIntensities);
//        ArrayAdapter adapter = new ArrayAdapter<String>(ViewDecisionDetailActivity.this,
//                R.layout.activity_listview, arrayOfMoods);
//        ListView listView = (ListView) findViewById(R.id.mobile_list);
//        SimpleListDisplay simpleListDisplay = new SimpleListDisplay(adapter, listView);
//        simpleListDisplay.displayData(arrayOfMoods);
//    }

    private String getFormattedDateString(@NotNull DecisionOptions decision) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return String.format("%s - %s",
                decision.getDecision().getStartDate().format(formatter),
                decision.getDecision().getEndDate().format(formatter));
    }
}

