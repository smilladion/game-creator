package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.R;

public class CreateTextFragment extends Fragment {

    EditText editText;
    Button doneButton;
    Button discardButton;
    ComponentDB cDB;
    TextComponent component; // Non-null when fragment was created through an edit button

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
            component = (TextComponent) cDB.getCurrentGame().getComponents().get(index);
        }

        return inflater.inflate(R.layout.fragment_create_text_component, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editText = view.findViewById(R.id.input_text);
        doneButton = view.findViewById(R.id.done_button);
        discardButton = view.findViewById(R.id.discard_button);

        if (component != null) {
            editText.setText(component.getText());
        }

        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::onDiscardClicked);
    }

    public void onDoneClicked(View view) {
        // If fragment was opened through edit button, then set that component's text and return
        // Otherwise create a new component and add it to the database
        if (component != null) {
            component.setText(editText.getText().toString());
        } else {
            TextComponent tc = new TextComponent(cDB.getNextId(), editText.getText().toString());
            cDB.getCurrentGame().addComponent(tc);
        }

        getParentFragmentManager().popBackStack(); // Close fragment and go back to editor
    }

    public void onDiscardClicked(View view) {
        getParentFragmentManager().popBackStack();
    }
}
