package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import dk.itu.gamecreator.android.Components.SolutionComponent;

public class TextSolutionComponent extends SolutionComponent {

    private String solutionText;
    private String buttonText;

    private boolean isCaseSensitive;
    EditText editText;

    public TextSolutionComponent(int id, String solutionText, String buttonText, boolean isCaseSensitive) {
        super(id);
        this.isCaseSensitive = isCaseSensitive;
        if (this.isCaseSensitive) {
            this.solutionText = solutionText;
        } else {
            this.solutionText = solutionText.toLowerCase();
        }
        this.buttonText = buttonText;

    }

    public View getView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        editText = new EditText(context);
        editText.setWidth(150);
        ll.addView(editText);

        Button button = new Button(context);
        button.setText(buttonText);
        ll.addView(button);
        button.setOnClickListener(view -> checkSolution(view, context));
        return ll;
    }

    public void checkSolution(View view, Context context) {
        String userSolution = editText.getText().toString().trim();
        if (!isCaseSensitive) {
            userSolution = userSolution.toLowerCase();
        }
        System.out.println("solution: " + solutionText);
        System.out.println("userSolution: " + userSolution);
        if (solutionText.equals(userSolution)) {
            Toast toast = Toast.makeText(context, "Correct!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public String getSolutionText() {
        return solutionText;
    }

    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String text) {
        this.buttonText = text;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }
}