package com.lydiaralph.decisiontracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lydiaralph.decisiontracker.database.adapter.DecisionListAdapter;
import com.lydiaralph.decisiontracker.database.entity.Decision;
import com.lydiaralph.decisiontracker.database.viewmodel.DecisionViewModel;

import java.util.List;

import static com.lydiaralph.decisiontracker.MainActivity.NEW_DECISION_ACTIVITY_REQUEST_CODE;

public class ConfigurationActivity extends AppCompatActivity {

    public static final String NEW_QUESTION = "com.ralph.lydia.NEW_QUESTION";

    private DecisionViewModel decisionViewModel;

    public static final String OPTION_1 = "com.ralph.lydia.OPTION_1";
    public static final String OPTION_2 = "com.ralph.lydia.OPTION_2";
    public static final String OPTION_3 = "com.ralph.lydia.OPTION_3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_new_question);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final DecisionListAdapter adapter = new DecisionListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        decisionViewModel = ViewModelProviders.of(this).get(DecisionViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        decisionViewModel.getAllDecisions().observe(this, new Observer<List<Decision>>() {
            @Override
            public void onChanged(@Nullable final List<Decision> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setDecisions(words);
            }
        });

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ConfigurationActivity.this, NewDecisionActivity.class);
                startActivityForResult(intent, NEW_DECISION_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_DECISION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Decision decision = new Decision(data.getStringExtra(NewDecisionActivity.NEW_QUESTION));
            decisionViewModel.insert(decision);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, ConfirmConfigurationActivity.class);
//        EditText editText = (EditText) findViewById(R.id.user_input_question);
//        String message = editText.getText().toString();
//        intent.putExtra(NEW_QUESTION, message);
//
//        EditText option1 = (EditText) findViewById(R.id.user_input_option_1);
//        intent.putExtra(OPTION_1, option1.getText().toString());
//
//        EditText option2 = (EditText) findViewById(R.id.user_input_option_2);
//        intent.putExtra(OPTION_2, option2.getText().toString());
//
//        EditText option3 = (EditText) findViewById(R.id.user_input_option_3);
//        intent.putExtra(OPTION_3, option3.getText().toString());
//
//        startActivity(intent);
//    }
}
