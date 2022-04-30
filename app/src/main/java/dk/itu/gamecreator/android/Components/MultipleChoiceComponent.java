package dk.itu.gamecreator.android.Components;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;

public class MultipleChoiceComponent extends SolutionComponent {

    private String[] options;
    private RadioGroup radioGroup;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private EditText text1;
    private EditText text2;
    private EditText text3;
    private EditText text4;
    private int correct;
    private int radioId;

    public MultipleChoiceComponent(int id) {
        super(id);
    }

    @Override
    public boolean saveComponent(Context context) {
        String one = text1.getText().toString().trim();
        String two = text2.getText().toString().trim();
        String three = text3.getText().toString().trim();
        String four = text4.getText().toString().trim();

        String[] options = {one, two, three, four};

        if (one.length() == 0
                || two.length() == 0
                || three.length() == 0
                || four.length() == 0) {
            Toast toast = Toast.makeText(context,
                    "Text fields can't be empty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        } else {
            this.options = options;
        }

        radioId = radioGroup.getCheckedRadioButtonId();

        return true;
    }

    @Override
    public View getDisplayView(Context context) {
        LinearLayout outer = new LinearLayout(context);
        outer.setOrientation(LinearLayout.VERTICAL);

        LinearLayout inner1 = new LinearLayout(context);
        LinearLayout inner2 = new LinearLayout(context);

        inner1.setOrientation(LinearLayout.HORIZONTAL);
        inner2.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < options.length; i++) {
            Button b = new Button(context);
            b.setText(options[i]);
            // it didn't just want 'i' in the lambda?
            int finalI = i;
            b.setOnClickListener(view -> checkSolution(view, finalI, context));
            if (i < 2) {
                inner1.addView(b);
            } else {
                inner2.addView(b);
            }
        }

        outer.addView(inner1);
        outer.addView(inner2);
        return outer;
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_multiple_choice, null, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1 = view.findViewById(R.id.radio_button_1);
        radio2 = view.findViewById(R.id.radio_button_2);
        radio3 = view.findViewById(R.id.radio_button_3);
        radio4 = view.findViewById(R.id.radio_button_4);
        text1 = view.findViewById(R.id.input_text1);
        text2 = view.findViewById(R.id.input_text2);
        text3 = view.findViewById(R.id.input_text3);
        text4 = view.findViewById(R.id.input_text4);

        radio1.setOnClickListener(v -> setCorrect(0));
        radio2.setOnClickListener(v -> setCorrect(1));
        radio3.setOnClickListener(v -> setCorrect(2));
        radio4.setOnClickListener(v -> setCorrect(3));

        if (options != null) {
            text1.setText(options[0]);
            text2.setText(options[1]);
            text3.setText(options[2]);
            text4.setText(options[3]);

            radioGroup.check(radioId);
        }

        return view;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public void checkSolution(View view, int index, Context context) {
        if (correct == index) {
            Toast toast = Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            setSolved(true);
        } else {
            Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
