package dk.itu.gamecreator.android.Components;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import dk.itu.gamecreator.android.Components.SolutionComponent;
import dk.itu.gamecreator.android.R;

public class TextSolutionComponent extends SolutionComponent {

    private String solutionTextS;
    private String buttonTextS;

    private boolean isCaseSensitive;
    EditText editText;
    EditText solutionText;
    EditText buttonText;
    CheckBox caseSensitiveBox;

    public TextSolutionComponent(int id) {
        super(id);
    }

    public View getDisplayView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        editText = new EditText(context);
        editText.setWidth(150);
        ll.addView(editText);

        Button button = new Button(context);
        button.setText(buttonTextS);
        ll.addView(button);
        button.setOnClickListener(view -> checkSolution(view, context));
        return ll;
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_text_solution_component, null, false);
        solutionText = view.findViewById(R.id.input_solution);
        buttonText = view.findViewById(R.id.input_button_text);
        caseSensitiveBox = view.findViewById(R.id.case_sensitive_checkbox);
        return view;
    }

    public void checkSolution(View view, Context context) {
        String userSolution = editText.getText().toString().trim();
        if (!isCaseSensitive) {
            userSolution = userSolution.toLowerCase();
            solutionTextS = solutionTextS.toLowerCase();
        }
        if (solutionTextS.equals(userSolution)) {
            Toast toast = Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public boolean saveComponent(Context context) {
        if (solutionText.getText().toString().trim().length() == 0) {
            Toast toast = Toast.makeText(context,
                    "Text field can't be empty. Write something, or discard component", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            isCaseSensitive = caseSensitiveBox.isChecked();
            solutionTextS = solutionText.getText().toString();
            buttonTextS = buttonText.getText().toString();
        }
        return true;
    }

    public String getSolutionText() {
        return solutionTextS;
    }

    public void setSolutionText(String solutionText) {
        this.solutionTextS = solutionText;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public String getButtonText() {
        return buttonTextS;
    }

    public void setButtonText(String text) {
        this.buttonTextS = text;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }
}
