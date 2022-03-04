package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.R;

public class ConfigFragment extends Fragment {

    ComponentDB cDB;
    EditText nameGameEdit;
    Button editLocationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();

        nameGameEdit = view.findViewById(R.id.game_name);
        nameGameEdit.setText(cDB.getCurrentGame().getName());
        nameGameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                cDB.getCurrentGame().setName(s.toString());
            }
        });

        editLocationButton = view.findViewById(R.id.edit_location_button);

        // Difficulty setting
        Spinner difficultySpinner = view.findViewById(R.id.difficulty_spinner);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);

        // Color theme setting
        Spinner colorSpinner = view.findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.color_array, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);

        // Number of tries setting
        Spinner noTriesSpinner = view.findViewById(R.id.no_tries_spinner);
        ArrayAdapter<CharSequence> noTriesAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.no_tries_array, android.R.layout.simple_spinner_item);
        noTriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noTriesSpinner.setAdapter(noTriesAdapter);
    }
}
