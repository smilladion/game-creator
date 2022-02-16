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
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.R;

public class CreateTextComponentFragment extends Fragment {

    EditText text;
    Button doneButton;
    Button discardButton;
    GameComponent gameComponent;
    ComponentDB cDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_text_component, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        cDB = ComponentDB.getInstance();

        text = view.findViewById(R.id.input_text);
        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::onDiscardClicked);
    }

    public void onDoneClicked(View view) {
        gameComponent = new TextComponent(cDB.getNextId(), text.getText().toString());
        cDB.getCurrentGame().addComponent(gameComponent);
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
        /*FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setReorderingAllowed(true);
        ft.replace(R.id.create_fragment, EditorFragment.class, null);
        ft.commit();*/
    }
}
