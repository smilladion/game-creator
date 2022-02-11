package com.bignerdranch.android.gamecreator;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class ButtonGroupComponent extends SolutionComponent {

    private String[] texts = new String[4];
    private Button[] buttons = new Button[4];
    private int correctButton;

    public ButtonGroupComponent(int id, int correctButton, String[] texts) {
        super(id);
        this.correctButton = correctButton;
        this.texts = texts;

        for (int i = 0; i < 4; i++) {
            buttons[i].setText(texts[i]);
            /* Set some click listeners - not sure if it should be done here? This component does
            not know (and shouldn't know?) what happens after it is clicked */
            if (i == correctButton) {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println("Correct!");
                    }
                });
            } else {
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println("Not correct.");
                    }
                });
            }
        }


    }

    public void setHeight(int height) {

        for (Button b: buttons) {
            b.setHeight(height);
        }
    }

    public void setWidth(int width) {
        for (Button b: buttons) {
            b.setWidth(width);
        }
    }

    public Button[] getButtons() {
        return buttons;
    }
}
