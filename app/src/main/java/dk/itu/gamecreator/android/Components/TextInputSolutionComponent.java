package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import dk.itu.gamecreator.android.Components.SolutionComponent;

public class TextInputSolutionComponent extends SolutionComponent {

    private String inputText;
    private String solutionText;
    private String buttonText;
    private TextView textView;
    private EditText editText;
    private Button button;

    public TextInputSolutionComponent(int id, String inputText, String solutionText, String buttonText) {
        super(id);
        this.inputText = inputText;
        this.solutionText = solutionText;
        this.buttonText = buttonText;
    }

    public View getView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        textView = new TextView(context);
        editText = new EditText(context);
        textView.setText(inputText);
        ll.addView(textView);
        ll.addView(editText);
        ll.addView(button);
        button.setOnClickListener(this::checkSolution);
        return ll;
    }

    // Is this possible? When the LinearLayout is returned, does it keep all the info?
    public void checkSolution(View view) {
        if (this.solutionText.equals(editText.getText().toString())) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getSolutionText() {
        return solutionText;
    }

    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}
