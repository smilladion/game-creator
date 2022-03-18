package dk.itu.gamecreator.android.Components;

import static android.view.Gravity.CENTER;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import dk.itu.gamecreator.android.R;

public class TextComponent extends GameComponent {

    private String text;
    private float size = 25;
    private String gravity;

    public TextComponent(int id, String text) {
        super(id);
        this.text = text;
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
    public View getCreateView(Context context) {
        return (LinearLayout) ((Activity) context).findViewById(R.id.text_layout);
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
