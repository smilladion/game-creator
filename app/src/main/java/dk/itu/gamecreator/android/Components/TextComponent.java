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
    private String gravity = "left";
    private TextInputEditText editText;
    private AutoCompleteTextView sizeView;
    private MaterialButtonToggleGroup toggleButton;
    private int buttonId;

    public TextComponent(int id) {
        super(id);
    }

    public View getDisplayView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView tw = new TextView(context);

        if (gravity != null) {
            if (gravity.equals("left")) {
                ll.setGravity(Gravity.LEFT);
            } else if (gravity.equals("center")) {
                ll.setGravity(Gravity.CENTER);
            } else {
                ll.setGravity(Gravity.RIGHT);
            }
        }

        tw.setTextSize(size);
        tw.setText(text);
        ll.addView(tw);
        return ll;
    }

    @Override
    public void saveComponent(Context context) {
        String text = editText.getText().toString().trim();

        if (text.length() == 0) {
            Toast toast = Toast.makeText(context,
                    "Text field can't be empty. Write something, or discard component", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            this.text = text;
        }

        buttonId = toggleButton.getCheckedButtonId();
        if (buttonId == R.id.text_left) {
            gravity = "left";
        } else if (buttonId == R.id.text_right) {
            gravity = "right";
        } else {
            gravity = "center";
        }

        String size = sizeView.getText().toString().trim();
        if (size.length() != 0) {
            this.size = Float.parseFloat(size);
        }
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_text_component, null, false);
        editText = view.findViewById(R.id.input_text_1);
        sizeView = view.findViewById(R.id.input_size);
        toggleButton = view.findViewById(R.id.toggleButton);

        editText.setText(text);
        sizeView.setText(Float.toString(size));

        if (buttonId != 0) {
            toggleButton.check(buttonId);
        } else {
            toggleButton.check(R.id.text_left);
        }

        return view;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public String getGravity() {
        return gravity;
    }

    public void setGravity(String pos) {
        this.gravity = pos;
    }
}
