package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import dk.itu.gamecreator.android.R;

public class ButtonOrderComponent extends SolutionComponent {

    private String[] options;
    private EditText text1;
    private EditText text2;
    private EditText text3;
    private EditText text4;
    private int[] correctOrder = {0, 1, 2, 3};
    private int[] clicked = new int[4];
    private Button[] buttons = new Button[4];
    private int counter = 0;

    public ButtonOrderComponent(int id) {
        super(id);
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
            buttons[i] = b;
            b.setText(options[i]);
            // it didn't just want 'i' in the lambda?
            int finalI = i;
            b.setOnClickListener(view -> checkSolution(view, finalI, b, context));
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

    public void checkSolution(View view, int clickedButton, Button button, Context context) {
        clicked[counter] = clickedButton;
        button.setEnabled(false);
        if (this.counter == 3) {
            boolean isCorrect = true;
            for (int i = 0; i < 4; i++) {
                if (clicked[i] != correctOrder[i]) {
                    isCorrect = false;
                }
            }
            if (isCorrect) {
                setSolved(true);
            } else {
                Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                counter = 0;
                for (Button b : buttons) {
                    b.setEnabled(true);
                }
            }
        } else {
            this.counter = counter + 1;
        }
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_button_order, null, false);
        text1 = view.findViewById(R.id.input_text1);
        text2 = view.findViewById(R.id.input_text2);
        text3 = view.findViewById(R.id.input_text3);
        text4 = view.findViewById(R.id.input_text4);
        return view;
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
        return true;
    }

    @Override
    public String getName() {
        return "Button Order";
    }
}
