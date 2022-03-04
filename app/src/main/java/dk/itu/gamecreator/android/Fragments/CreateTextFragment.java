package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.R;

public class CreateTextFragment extends Fragment {

    Button doneButton;
    Button discardButton;
    TextInputEditText editText;
    ComponentDB cDB;
    MaterialButtonToggleGroup toggleButton;
    AutoCompleteTextView sizeInput;
    TextComponent component; // Non-null when fragment was created through an edit button
    static float DEFAULT_TEXT_SIZE = 50;

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
        toggleButton = view.findViewById(R.id.toggleButton);
        sizeInput = view.findViewById(R.id.input_size);

        if (component != null) {
            editText.setText(component.getText());
            //sets it as float - does not look so good.
            sizeInput.setText(String.valueOf(component.getSize()));
        }

        doneButton.setOnClickListener(this::onDoneClicked);
        discardButton.setOnClickListener(this::onDiscardClicked);
    }

    public void onDoneClicked(View view) {
        // If fragment was opened through edit button, then set that component's text and return
        // Otherwise create a new component and add it to the database
        String pos;
        int buttonId = toggleButton.getCheckedButtonId();
        if (buttonId == R.id.text_left) {
            pos = "left";
        } else if (buttonId == R.id.text_right) {
            pos = "right";
        } else {
            pos = "center";
        }

        //Check if sizeinput is empty - if nothing given set default 50
        float size;
        if (sizeInput.getText().toString().trim().length() == 0) {
            size = DEFAULT_TEXT_SIZE;
        } else {
            size = Float.parseFloat(sizeInput.getText().toString());
        }

        if (editText.getText().toString().trim().length() == 0) {
            Toast toast = Toast.makeText(this.getContext(),
                    "Text field can't be empty. Write something, or discard component", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (component != null) {
                component.setText(editText.getText().toString());
                component.setPos(pos);
                component.setSize(size);
            } else {
                TextComponent tc = new TextComponent(cDB.getNextComponentId(), editText.getText().toString());
                tc.setPos(pos);
                tc.setSize(size);
                cDB.getCurrentGame().addComponent(tc);
            }
            //((EditorFragment) getParentFragment()).setButtonsEnabled(true);
            getParentFragmentManager().popBackStack(); // Close fragment and go back to editor
        }
    }

    public void onDiscardClicked(View view) {
        //((EditorFragment) getParentFragment()).setButtonsEnabled(true);
        getParentFragmentManager().popBackStack();
    }
}
