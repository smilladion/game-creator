package dk.itu.gamecreator.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import dk.itu.gamecreator.android.SolutionComponent;

public class TextInputSolutionComponent extends SolutionComponent {

    private String inputText;
    private String solutionText;
    private String promptText;
    private TextView textView;
    private EditText editText;

    public TextInputSolutionComponent(int id, String inputText, String solutionText, String promptText) {
        super(id);
        this.inputText = inputText;
        this.solutionText = solutionText;
        this.promptText = promptText;
        textView.setText(inputText);
        editText.setText(promptText);
    }

    public View getView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(textView);
        ll.addView(editText);
        return ll;
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

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
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
