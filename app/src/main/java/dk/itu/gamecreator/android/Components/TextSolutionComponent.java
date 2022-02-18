package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import dk.itu.gamecreator.android.Components.SolutionComponent;

public class TextSolutionComponent extends SolutionComponent {

    private String solutionText;
    private String buttonText;
    EditText editText;

    public TextSolutionComponent(int id, String solutionText, String buttonText) {
        super(id);
        this.solutionText = solutionText;
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
}
