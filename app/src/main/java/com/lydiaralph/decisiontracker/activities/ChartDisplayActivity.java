package com.lydiaralph.decisiontracker.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.fragments.ArticleFragment;
import com.lydiaralph.decisiontracker.fragments.HeadlinesFragment;

import androidx.fragment.app.FragmentActivity;

public class ChartDisplayActivity extends FragmentActivity {
    private static final String LOG_NAME = ChartDisplayActivity.class.getSimpleName();
//    private Spinner chooseChartOptionSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_NAME, "Starting activity");
        setContentView(R.layout.chart_display);


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            Log.i(LOG_NAME, "Creating fragments");

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            HeadlinesFragment firstFragment = new HeadlinesFragment();
            ArticleFragment secondFragment = new ArticleFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());
            secondFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment)
//                    .add(R.id.fragment_container, secondFragment)
                    .commit();

//            chooseChartOptionSpinner = findViewById(R.id.spinner1);

        }
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        chooseChartOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
//                Toast.makeText(parent.getContext(),
//                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing
//            }
//
//        });
//    }
}