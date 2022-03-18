package dk.itu.gamecreator.android.Components;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import dk.itu.gamecreator.android.R;

public class MultipleChoiceSolution extends SolutionComponent {

    private String[] options;
    private int solution;

    public MultipleChoiceSolution(int id, String[] options, int solution) {
        super(id);
        this.options = options;
        this.solution = solution;
    }

    @Override
    public View getDisplayView(Context context) {
        LinearLayout outer = new LinearLayout(context);
        LinearLayout inner1 = new LinearLayout(context);
        LinearLayout inner2 = new LinearLayout(context);
        outer.setOrientation(LinearLayout.VERTICAL);
        inner1.setOrientation(LinearLayout.HORIZONTAL);
        inner2.setOrientation(LinearLayout.HORIZONTAL);
        for(int i = 0; i < options.length; i++) {
            Button b = new Button(context);
            b.setText(options[i]);
            // it didnt just want 'i' in the lambda?
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
        return (LinearLayout) ((Activity) context).findViewById(R.id.multiple_choice_layout);
    }

    public void checkSolution(View view, int index, Context context) {
        if (solution == index) {
            Toast toast = Toast.makeText(context, "Correct!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public String getGravity() {
        return "center";
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }
}
