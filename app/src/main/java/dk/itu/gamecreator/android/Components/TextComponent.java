package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import dk.itu.gamecreator.android.Components.GameComponent;

public class TextComponent extends GameComponent {

    private String text;

    public TextComponent(int id, String text) {
        super(id);
        this.text = text;
    }

    public View getView(Context context) {
        TextView tw = new TextView(context);
        tw.setText(text);
        return tw;
    }

    public String getText() {
        return text;
    }
}
