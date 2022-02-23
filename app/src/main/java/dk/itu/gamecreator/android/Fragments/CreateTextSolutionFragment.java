package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.TextSolutionComponent;
import dk.itu.gamecreator.android.R;

public class CreateTextSolutionFragment extends Fragment {

    ComponentDB cDB;
    EditText solutionText;
    EditText buttonText;
    Button doneButton;
    Button discardButton;
    TextSolutionComponent component; // Non-null when fragment was created through an edit button

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();
        Bundle bundle = getArguments();

        // Checks if the fragment was opened through an edit button and fetches component data
        if (bundle != null) {
            int index = bundle.getInt("componentIndex");
            component = (TextSolutionComponent) cDB.getCurrentGame().getComponents().get(index);
        }

        return inflater.inflate(R.layout.fragment_create_text_solution_component, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        cDB = ComponentDB.getInstance();

        solutionText = view.findViewById(R.id.input_solution);
        buttonText = view.findViewById(R.id.input_button_text);
        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        if (component != null) {
            solutionText.setText(component.getSolutionText());
            buttonText.setText(component.getButtonText());
        }

        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::onDiscardClicked);
    }

    public void onDoneClicked(View view) {
        // If fragment was opened through edit button, then set that component's content and return
        // Otherwise create a new component and add it to the database
        if (component != null) {
            component.setSolutionText(solutionText.getText().toString());
            component.setButtonText(buttonText.getText().toString());
        } else {
            String solution = solutionText.getText().toString();
            String button = buttonText.getText().toString();
            TextSolutionComponent sc = new TextSolutionComponent(cDB.getNextId(), solution, button);
            cDB.getCurrentGame().addComponent(sc);
        }

        ((CreateActivity) getActivity()).setButtonsEnabled(true);
        getParentFragmentManager().popBackStack(); // Close fragment and go back to editor
    }

    public void onDiscardClicked(View view) {
        ((CreateActivity) getActivity()).setButtonsEnabled(true);
        getParentFragmentManager().popBackStack();
    }
}
