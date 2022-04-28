package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import dk.itu.gamecreator.android.R;

public class PinCodeSolutionComponent extends SolutionComponent {

    private static final Random RANDOM = new Random();
    private final Button[] buttons = new Button[4];
    private EditText editCode;
    private String requiredCode;
    private int currentDigit;

    public PinCodeSolutionComponent(int id) {
        super(id);
    }

    @Override
    public String getName() {
        return "PIN Code";
    }

    @Override
    public View getCreateView(Context context) {
        View view = LayoutInflater.from(context).inflate(
            R.layout.fragment_create_code_component,
            null,
            false
        );

        editCode = view.findViewById(R.id.edit_code);

        return view;
    }

    @Override
    public View getDisplayView(Context context) {
        View view = LayoutInflater.from(context).inflate(
            R.layout.fragment_display_code_component,
            null,
            false
        );

        buttons[0] = view.findViewById(R.id.code_1);
        buttons[1] = view.findViewById(R.id.code_2);
        buttons[2] = view.findViewById(R.id.code_3);
        buttons[3] = view.findViewById(R.id.code_4);

        for (Button button : buttons) {
            button.setOnClickListener(v -> numberPressed(button.getText().charAt(0)));
        }

        updateNumber();

        return view;
    }

    @Override
    public boolean saveComponent(Context context) {
        String text = editCode.getText().toString().trim();

        if (text.matches("\\d+") && text.length() == 4) {
            requiredCode = text;
            return true;
        }

        Toast toast = Toast.makeText(context, "Code must be 4 digits", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        return false;
    }

    private void numberPressed(char number) {
        if (requiredCode.charAt(currentDigit) != number) {
            currentDigit = 0;
        } else {
            currentDigit++;
        }

        if (currentDigit == 4) {
            setSolved(true);
        } else {
            updateNumber();
        }
    }

    private void updateNumber() {
        Button correctButton = buttons[RANDOM.nextInt(buttons.length)];

        correctButton.setText(String.valueOf(requiredCode.charAt(currentDigit)));

        for (Button button : buttons) {
            if (button == correctButton) {
                continue;
            }

            button.setText(String.valueOf(RANDOM.nextInt(10)));
        }
    }
}
