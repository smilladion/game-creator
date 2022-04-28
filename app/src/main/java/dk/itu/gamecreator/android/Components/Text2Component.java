package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dk.itu.gamecreator.android.R;

public class Text2Component extends GameComponent {

    private TextView textView;
    private EditText editText;

    private String displayText;

    /**
     * Define all the different views your getCreateView will need, ie:
     * EditText editText;
     *
     * @param id
     */
    public Text2Component(int id) {
        super(id);
    }

    @Override
    public View getDisplayView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_display_text2_component,
                null,
                false
        );

        textView = view.findViewById(R.id.display_text);

        textView.setText(displayText);

        return view;
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_create_text2_component,
                null,
                false
        );

        editText = view.findViewById(R.id.edit_display_text);

        if (displayText != null) {
            editText.setText(displayText);
        }

        return view;
    }

    @Override
    public boolean saveComponent(Context context) {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast toast = Toast.makeText(context, "Text can't be empty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return false;
        }

        displayText = editText.getText().toString().trim();

        return true;
    }

    @Override
    public String getName() {
        return "Text Two";
    }
}
