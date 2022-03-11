package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MultipleChoiceSolution extends SolutionComponent {

    private String[] options;
    private int solution;

    public MultipleChoiceSolution(int id, String[] options, int solution) {
        super(id);
        this.options = options;
        this.solution = solution;
    }

    @Override
    public View getView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        for(int i = 0; i < options.length; i++) {
            Button b = new Button(context);
            b.setText(options[i]);
            // it didnt just want 'i' in the lambda?
            int finalI = i;
            b.setOnClickListener(view -> checkSolution(view, finalI, context));
            ll.addView(b);
        }
        return ll;
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
}
