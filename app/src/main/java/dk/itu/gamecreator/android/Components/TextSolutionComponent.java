package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import dk.itu.gamecreator.android.R;

public class TextSolutionComponent extends SolutionComponent {

    private String solutionTextS;
    private String buttonTextS;

    private boolean isCaseSensitive;
    private EditText editText;
    private EditText solutionText;
    private EditText buttonText;
    private CheckBox caseSensitiveBox;

    public TextSolutionComponent(int id) {
        super(id);
        isCaseSensitive = false;
    }

    public View getDisplayView(Context context) {
        LinearLayout outerLayout = new LinearLayout(context);
        outerLayout.setOrientation(LinearLayout.HORIZONTAL);

        editText = new EditText(context);
        editText.setWidth(300);

        outerLayout.addView(editText);

        Button button = new Button(context);
        button.setText(buttonTextS);
        outerLayout.addView(button);
        button.setOnClickListener(view -> checkSolution(view, context));
        return outerLayout;
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_text_solution_component, null, false);
        solutionText = view.findViewById(R.id.input_solution);
        buttonText = view.findViewById(R.id.input_button_text);
        caseSensitiveBox = view.findViewById(R.id.case_sensitive_checkbox);

        solutionText.setText(solutionTextS);
        buttonText.setText(buttonTextS);
        caseSensitiveBox.setChecked(isCaseSensitive);

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
            setSolved(true);
        } else {
            Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public boolean saveComponent(Context context) {
        String sText = solutionText.getText().toString().trim();
        String bText = buttonText.getText().toString().trim();

        if (sText.length() == 0 || bText.length() == 0) {
            Toast toast = Toast.makeText(context,
                    "Text fields can't be empty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        isCaseSensitive = caseSensitiveBox.isChecked();
        solutionTextS = solutionText.getText().toString();
        buttonTextS = buttonText.getText().toString();
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
