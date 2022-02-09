package com.bignerdranch.android.gamecreator;

import android.graphics.Color;
import android.widget.Button;

public class ButtonComponent {

    private String text;
    private Button button;

    public ButtonComponent(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
    }

    public void setHeight(int height) {
        button.setHeight(height);
    }

    public void setWidth(int width) {
        button.setWidth(width);
    }
}
