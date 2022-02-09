package com.bignerdranch.android.gamecreator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText password_editText;
    private EditText passwordAnswer_editText;
    private Button submit_button;
    private Button submit_answer_button;

    private EditText dynamicEdit;
    private Button dynamicButton;

    private LinearLayout ll;

    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password_editText = findViewById(R.id.password_text);
        passwordAnswer_editText = findViewById(R.id.password_text_answer);
        submit_button = findViewById(R.id.password_button);
        submit_answer_button = findViewById(R.id.password_button_answer);

        dynamicEdit = findViewById(R.id.dynamicTestText);
        dynamicButton = findViewById(R.id.dynamicButton);
        ll = findViewById(R.id.linearLayoutTest);

        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
*/
        dynamicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                String prompt = dynamicEdit.getText().toString().toLowerCase().trim();
                switch (prompt) {
                    case "text":
                        TextView text = new TextView(context);
                        text.setText("Some text");
                        ll.addView(text);
                        break;
                    case "button":
                        Button button = new Button(context);
                        button.setText("Some button");
                        ll.addView(button);
                        break;
                    default:
                }
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                password = password_editText.getText().toString().trim();
                password_editText.getText().clear();
            }
        });

        submit_answer_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String check = passwordAnswer_editText.getText().toString().trim();
                Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                if (password.equals(check)) {
                    toast.setText("Correct!");
                    toast.show();
                } else {
                    toast.setText("Incorrect.");
                    toast.show();
                }
            }
        });

    }
}