package dk.itu.gamecreator.android;

import android.widget.Button;

public class ButtonGroupComponent extends SolutionComponent {

    private String[] texts = new String[4];
    private Button[] buttons = new Button[4];

    public ButtonGroupComponent(int id, String[] texts) {
        super(id);
        this.texts = texts;
        for (int i = 0; i < 4; i++) {
            buttons[i].setText(texts[i]);
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
