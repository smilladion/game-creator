package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import dk.itu.gamecreator.android.R;

public class NextComponent extends SolutionComponent {

    private String buttonText = "Next";
    private EditText buttonEdit;

    public NextComponent(int id) {
        super(id);
    }

    @Override
    public boolean saveComponent(Context context) {
        buttonText = buttonEdit.getText().toString().trim();
        return true;
    }

    @Override
    public View getDisplayView(Context context) {
        LinearLayout outerLayout = new LinearLayout(context);
        Button button = new Button(context);
        button.setText(buttonText);
        button.setOnClickListener(v ->
                setSolved(true));
        outerLayout.addView(button);
        return outerLayout;
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_next_component, null, false);
        buttonEdit = view.findViewById(R.id.input_solution);
        return view;
    }

}
