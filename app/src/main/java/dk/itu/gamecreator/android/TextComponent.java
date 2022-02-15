package dk.itu.gamecreator.android;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TextComponent extends GameComponent {

    private TextView textView;
    private String text;

    public TextComponent(int id, String text) {
        super(id);
        this.text = text;
        textView.setText(text);
    }

    public View getView(Context context) {
        return textView;
    }
}
