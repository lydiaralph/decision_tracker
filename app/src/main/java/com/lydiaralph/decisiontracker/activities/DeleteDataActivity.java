package com.lydiaralph.decisiontracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.lydiaralph.decisiontracker.database.viewmodel.MoodTypeViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.MoodViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.OptionViewModel;
import com.lydiaralph.decisiontracker.database.viewmodel.VoteViewModel;
import com.lydiaralph.decisiontracker.fragments.DeleteDataFragment;
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

public class DeleteDataActivity extends FragmentActivity implements DeleteDataFragment.Listener {

    private static final String LOG_NAME = DeleteDataActivity.class.getSimpleName();

    @Inject
    DecisionViewModelFactory viewModelFactory;

    private DecisionViewModel decisionViewModel;
    private MoodViewModel moodViewModel;
    private OptionViewModel optionViewModel;
    private VoteViewModel voteViewModel;
    private MoodTypeViewModel moodTypeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Log.i(LOG_NAME, "Starting activity");

        decisionViewModel = ViewModelProviders.of(this, viewModelFactory).get(DecisionViewModel.class);
        moodViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoodViewModel.class);
        optionViewModel = ViewModelProviders.of(this, viewModelFactory).get(OptionViewModel.class);
        voteViewModel = ViewModelProviders.of(this, viewModelFactory).get(VoteViewModel.class);
        moodTypeViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoodTypeViewModel.class);

        DeleteDataFragment deleteDataFragment = DeleteDataFragment.newInstance();
        deleteDataFragment.show(getSupportFragmentManager(), "terminateDecision");
    }

    @Override
    public void deleteAllData(DialogFragment dialog) {
        moodViewModel.deleteAll();
        voteViewModel.deleteAll();
        optionViewModel.deleteAll();
        moodTypeViewModel.deleteAll();
        decisionViewModel.deleteAll();

        Toast.makeText(getApplicationContext(), "All data deleted", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent(DeleteDataActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK, resultIntent);
        startActivity(resultIntent);
        finish();
    }

    @Override
    public void cancelDelete(DialogFragment dialogFragment){
        Toast.makeText(getApplicationContext(), "No data deleted", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent(DeleteDataActivity.this, MainActivity.class);
        setResult(Activity.RESULT_OK, resultIntent);
        startActivity(resultIntent);
        finish();
    }

}

