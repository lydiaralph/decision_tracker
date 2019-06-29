package com.lydiaralph.decisiontracker.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.fragments.ArticleFragment;
import com.lydiaralph.decisiontracker.fragments.HeadlinesFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

public class ChartDisplayActivity extends FragmentActivity {
    private static final String LOG_NAME = ChartDisplayActivity.class.getSimpleName();
    private Spinner chooseChartOptionSpinner;

    private HeadlinesFragment headlinesFragment;
    private ArticleFragment articleFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_NAME, "Starting activity");
        setContentView(R.layout.chart_display);

        if (findViewById(R.id.fragment_container) != null) {
            Log.i(LOG_NAME, "Creating fragments");

            if (savedInstanceState != null) {
                return;
            }

            headlinesFragment = new HeadlinesFragment();
            articleFragment = new ArticleFragment();

            headlinesFragment.setArguments(getIntent().getExtras());
            articleFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, articleFragment)
                    .commit();

            addItemsOnSpinner();

        }
    }

    public void addItemsOnSpinner() {
        chooseChartOptionSpinner = findViewById(R.id.spinner1);
        List<String> list = new ArrayList<>();
        list.add("Articles");
        list.add("Headlines");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseChartOptionSpinner.setAdapter(dataAdapter);

        chooseChartOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {

                String chosenItem = parent.getItemAtPosition(position).toString();

                if(chosenItem.equals("Headlines")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, headlinesFragment)
                            .commit();
                } else if (chosenItem.equals("Articles")){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, articleFragment)
                            .commit();
                }

                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }

        });
    }
}