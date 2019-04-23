package com.lydiaralph.decisiontracker.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.R;

public class ConfirmConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4_confirm_new_question);

        Intent intent = getIntent();
        String newQuestion = intent.getStringExtra(ConfigurationActivity.NEW_QUESTION);
        String option1 = intent.getStringExtra(ConfigurationActivity.OPTION_1);
        String option2 = intent.getStringExtra(ConfigurationActivity.OPTION_2);
        String option3 = intent.getStringExtra(ConfigurationActivity.OPTION_3);

        TextView questionView = findViewById(R.id.display_input_question);
        questionView.setText(String.format("%s", newQuestion));

        TextView optionsView = findViewById(R.id.display_input_options);
        optionsView.setText(String.format("%s\n%s\n%s",
                getString(R.string.display_options),
                option1, option2, option3));
    }


}
