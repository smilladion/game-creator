package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import dk.itu.gamecreator.android.Components.GameComponent;

public class TextComponent extends GameComponent {

    private String text;
    private LinearLayout ll;
    private float size = 25;
    private String pos;

    public TextComponent(int id, String text) {
        super(id);
        this.text = text;
    }

    public View getView(Context context) {
        ll = new LinearLayout(context);
        TextView tw = new TextView(context);
        ll.addView(tw);
        if (pos != null) {
            if (pos.equals("left")) {
                ll.setGravity(Gravity.LEFT);
            } else if (pos.equals("center")) {
                ll.setGravity(Gravity.CENTER);
            } else {
                ll.setGravity(Gravity.RIGHT);
            }
        }
        tw.setTextSize(size);
        tw.setText(text);
        return ll;
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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
