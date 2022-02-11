package dk.itu.gamecreator.android;

import android.widget.TextView;

public class TextComponent extends GameComponent {

    private TextView textView;
    private String text;

    public TextComponent(int id, String text) {
        super(id);
        this.text = text;
        textView.setText(text);
    }

    public TextView getTextView() {
        return textView;
    }
}
