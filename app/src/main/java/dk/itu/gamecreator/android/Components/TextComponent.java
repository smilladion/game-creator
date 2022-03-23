package dk.itu.gamecreator.android.Components;

import static android.view.Gravity.CENTER;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import dk.itu.gamecreator.android.R;

public class TextComponent extends GameComponent {

    private String text;
    private float size = 25;
    private String gravity;
    TextInputEditText editText;
    //MaterialButtonToggleGroup toggleButton;

    public TextComponent(int id) {
        super(id);
    }

    public View getDisplayView(Context context) {
        LinearLayout ll = new LinearLayout(context);
        TextView tw = new TextView(context);
        ll.addView(tw);
        if (gravity != null) {
            if (gravity.equals("left")) {
                ll.setGravity(Gravity.LEFT);
            } else if (gravity.equals("center")) {
                ll.setGravity(CENTER);
            } else {
                ll.setGravity(Gravity.RIGHT);
            }
        }
        tw.setTextSize(size);
        tw.setText(text);
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
    }

    @Override
    public View getCreateView(Context context) {
        //View view = (LinearLayout) ((Activity) context).findViewById(R.id.text_layout);
        //editText = (TextInputEditText) ((Activity) context).findViewById(R.id.input_text_1);
        //EditText editText = view.findViewById(R.id.input_text_1);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_create_text_component, null, false);
        editText = view.findViewById(R.id.input_text_1);
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
