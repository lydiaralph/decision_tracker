package com.lydiaralph.decisiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewDecisionActivity extends AppCompatActivity {

    public static final String NEW_QUESTION = "com.ralph.lydia.NEW_QUESTION";

    private EditText newDecisionText;

    public static final String OPTION_1 = "com.ralph.lydia.OPTION_1";
    public static final String OPTION_2 = "com.ralph.lydia.OPTION_2";
    public static final String OPTION_3 = "com.ralph.lydia.OPTION_3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_new_question);
        newDecisionText = findViewById(R.id.user_input_question);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(newDecisionText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String decision = newDecisionText.getText().toString();
                    replyIntent.putExtra(NEW_QUESTION, decision);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ConfirmConfigurationActivity.class);
        EditText editText = (EditText) findViewById(R.id.user_input_question);
        String message = editText.getText().toString();
        intent.putExtra(NEW_QUESTION, message);

//        EditText option1 = (EditText) findViewById(R.id.user_input_option_1);
//        intent.putExtra(OPTION_1, option1.getText().toString());
//
//        EditText option2 = (EditText) findViewById(R.id.user_input_option_2);
//        intent.putExtra(OPTION_2, option2.getText().toString());
//
//        EditText option3 = (EditText) findViewById(R.id.user_input_option_3);
//        intent.putExtra(OPTION_3, option3.getText().toString());

        startActivity(intent);
    }
}
