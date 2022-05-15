package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import dk.itu.gamecreator.android.R;

public class TextComponent extends GameComponent {

    private String text = "";
    private float size = 25;
    private int gravityButtonId;
    private TextInputEditText editText;
    private AutoCompleteTextView sizeView;
    private MaterialButtonToggleGroup toggleButton;

    public TextComponent(int id) {
        super(id);
    }

    public View getDisplayView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tw = new TextView(context);

        if (gravityButtonId == R.id.text_right) {
            ll.setGravity(Gravity.RIGHT);
        } else if (gravityButtonId == R.id.text_center) {
            ll.setGravity(Gravity.CENTER);
        } else {
            ll.setGravity(Gravity.LEFT);
        }

        tw.setTextSize(size);
        tw.setText(text);
        ll.addView(tw);

        return ll;
    }

    @Override
    public boolean saveComponent(Context context) {
        String text = editText.getText().toString().trim();

        if (text.length() == 0) {
            Toast toast = Toast.makeText(context,
                    "Text field can't be empty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }

        this.text = text;
        gravityButtonId = toggleButton.getCheckedButtonId();

        String size = sizeView.getText().toString().trim();
        if (size.length() != 0) {
            this.size = Float.parseFloat(size);
        }

        return true;
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_text_component, null, false);

        editText = view.findViewById(R.id.input_text_1);
        sizeView = view.findViewById(R.id.input_size);
        toggleButton = view.findViewById(R.id.toggleButton);

        editText.setText(text);
        sizeView.setText(Float.toString(size));

        if (gravityButtonId != 0) {
            toggleButton.check(gravityButtonId);
        } else {
            toggleButton.check(R.id.text_left);
        }

        return view;
    }

    public String getText() {
        return text;
    }

    public float getSize() {
        return size;
    }
}
