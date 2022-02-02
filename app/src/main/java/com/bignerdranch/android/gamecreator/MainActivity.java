package com.bignerdranch.android.gamecreator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText password_editText;
    private EditText passwordAnswer_editText;
    private Button submit_button;
    private Button submit_answer_button;

    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password_editText = findViewById(R.id.password_text);
        passwordAnswer_editText = findViewById(R.id.password_text_answer);
        submit_button = findViewById(R.id.password_button);
        submit_answer_button = findViewById(R.id.password_button_answer);

        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                password = password_editText.getText().toString().trim();
                password_editText.getText().clear();
            }
        });

        submit_answer_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String check = passwordAnswer_editText.getText().toString().trim();
                if (password.equals(check)) {
                    Toast.makeText(getApplicationContext(),"Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Incorrect.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}