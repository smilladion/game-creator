package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.MultipleChoiceSolution;
import dk.itu.gamecreator.android.Components.TextSolutionComponent;
import dk.itu.gamecreator.android.R;

public class CreateMultipleChoiceFragment extends Fragment {

    ComponentDB cDB;
    MultipleChoiceSolution component;
    RadioGroup radioGroup;
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;
    RadioButton radio4;
    EditText text1;
    EditText text2;
    EditText text3;
    EditText text4;
    Button doneButton;
    Button discardButton;
    Button closeButton;
    int correct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();
        Bundle bundle = getArguments();

        if (bundle != null) {
            int index = bundle.getInt("componentIndex");
            component = (MultipleChoiceSolution) cDB.getCurrentGame().getComponents().get(index);
        }

        return inflater.inflate(R.layout.fragment_create_multiple_choice, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1 = view.findViewById(R.id.radio_button_1);
        radio2 = view.findViewById(R.id.radio_button_2);
        radio3 = view.findViewById(R.id.radio_button_3);
        radio4 = view.findViewById(R.id.radio_button_4);
        text1 = view.findViewById(R.id.input_text1);
        text2 = view.findViewById(R.id.input_text2);
        text3 = view.findViewById(R.id.input_text3);
        text4 = view.findViewById(R.id.input_text4);
        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        if (component != null) {
            String[] options = component.getOptions();
            text1.setText(options[0]);
            text2.setText(options[1]);
            text3.setText(options[2]);
            text4.setText(options[3]);
        }
        radio1.setOnClickListener(v -> setCorrect(0));
        radio2.setOnClickListener(v -> setCorrect(1));
        radio3.setOnClickListener(v -> setCorrect(2));
        radio4.setOnClickListener(v -> setCorrect(3));
        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::onDiscardClicked);
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public void onDoneClicked(View view) {
        String one = text1.getText().toString().trim();
        String two = text2.getText().toString().trim();
        String three = text3.getText().toString().trim();
        String four = text4.getText().toString().trim();
        String[] options = {one, two, three, four};
        if (one.length() == 0
                || two.length() == 0
                || three.length() == 0
                || four.length() == 0) {
            Toast toast = Toast.makeText(this.getContext(),
                    "Text fields can't be empty. Write something, or discard component", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (component != null) {
                component.setOptions(options);
                component.setSolution(correct);
            } else {
                component = new MultipleChoiceSolution(cDB.getNextComponentId(), options, correct);
                cDB.getCurrentGame().addComponent(component);
            }
            getParentFragmentManager().popBackStack();
        }
    }

    public void onDiscardClicked(View view) {
        //((EditorFragment) getParentFragment()).setButtonsEnabled(true);
        getParentFragmentManager().popBackStack();
    }


}
