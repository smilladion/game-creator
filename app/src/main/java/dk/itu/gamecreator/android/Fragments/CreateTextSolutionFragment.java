package dk.itu.gamecreator.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.SolutionComponent;
import dk.itu.gamecreator.android.Components.TextSolutionComponent;
import dk.itu.gamecreator.android.R;

public class CreateTextSolutionFragment extends Fragment {

    ComponentDB cDB;
    EditText solutionText;
    EditText buttonText;
    Button doneButton;
    Button discardButton;
    SolutionComponent solutionComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_text_solution_component, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        cDB = ComponentDB.getInstance();

        solutionText = view.findViewById(R.id.input_solution);
        buttonText = view.findViewById(R.id.input_button_text);
        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::onDiscardClicked);
    }

    public void onDoneClicked(View view) {
        String solution = solutionText.getText().toString();
        String button = buttonText.getText().toString();
        solutionComponent = new TextSolutionComponent(cDB.getNextId(), solution, button);
        cDB.getCurrentGame().addSolution(solutionComponent);

        closeFragment();
    }

    public void onDiscardClicked(View view) {
        Intent intent = new Intent(this.getContext(), CreateActivity.class);
        startActivity(intent);
    }
    //HERE IS PROBLEM - the fragment does not know create_fragment
    public void closeFragment() {
        Intent intent = new Intent(this.getContext(), CreateActivity.class);
        startActivity(intent);
    }
}
