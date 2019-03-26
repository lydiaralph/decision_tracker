package com.lydiaralph.decisiontracker;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ConfigurationActivity extends AppCompatActivity {

    public static final String NEW_QUESTION = "com.ralph.lydia.NEW_QUESTION";

    public static final String OPTION_1 = "com.ralph.lydia.OPTION_1";
    public static final String OPTION_2 = "com.ralph.lydia.OPTION_2";
    public static final String OPTION_3 = "com.ralph.lydia.OPTION_3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_configuration);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ConfirmConfigurationActivity.class);
        EditText editText = (EditText) findViewById(R.id.user_input_question);
        String message = editText.getText().toString();
        intent.putExtra(NEW_QUESTION, message);

        EditText option1 = (EditText) findViewById(R.id.user_input_option_1);
        intent.putExtra(OPTION_1, option1.getText().toString());

        EditText option2 = (EditText) findViewById(R.id.user_input_option_2);
        intent.putExtra(OPTION_2, option2.getText().toString());

        EditText option3 = (EditText) findViewById(R.id.user_input_option_3);
        intent.putExtra(OPTION_3, option3.getText().toString());

        startActivity(intent);
    }
}
