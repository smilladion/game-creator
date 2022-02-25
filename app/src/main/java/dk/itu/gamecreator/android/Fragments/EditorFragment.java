package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Adapters.ItemMoveCallback;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Adapters.RecyclerViewAdapter;

public class EditorFragment extends Fragment {

    ComponentDB cDB;
    RecyclerViewAdapter adapter;
    Button saveGame;
    RecyclerView recyclerView;
    EditText nameGameEdit;

    Button createTextButton;
    Button createTextSolutionButton;
    Button createImageButton;

    FragmentContainerView fragmentContainerView;
    Fragment textFragment = new CreateTextFragment();
    Fragment textSolutionFragment = new CreateTextSolutionFragment();
    Fragment imageFragment = new CreateImageFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();
        if (cDB.getCurrentGame() == null) {
            cDB.newGame();
        }
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.current_components);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        fragmentContainerView = view.findViewById(R.id.component_fragment);

        nameGameEdit = view.findViewById(R.id.game_name_edit);

        createTextButton = view.findViewById(R.id.create_text_button);
        createTextButton.setOnClickListener(v -> openComponentFragment(v, textFragment));

        createTextSolutionButton = view.findViewById(R.id.create_text_solution_button);
        createTextSolutionButton.setOnClickListener(v -> openComponentFragment(v, textSolutionFragment));

        createImageButton = view.findViewById(R.id.create_image_button);
        createImageButton.setOnClickListener(v -> openComponentFragment(v, imageFragment));

        saveGame = view.findViewById(R.id.save_game_button);
        saveGame.setOnClickListener(this::saveGame);
        populateRecyclerView(view);
    }

    public void openComponentFragment(View view, Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, fragment, null)
                .addToBackStack(null)
                .commit();
        setButtonsEnabled(false);
    }

    public void populateRecyclerView(View view) {

        if (cDB.getCurrentGame() == null) {
            cDB.newGame();
        }

        adapter = new RecyclerViewAdapter(this.getContext(), cDB.getCurrentGame().getComponents());

        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
    }

    public void setButtonsEnabled(boolean isEnabled) {
        createTextButton.setEnabled(isEnabled);
        createTextSolutionButton.setEnabled(isEnabled);
        createImageButton.setEnabled(isEnabled);
    }

    public void saveGame(View view) {
        if (cDB.getCurrentGame().getComponents().isEmpty()) {
            Toast toast = Toast.makeText(this.getContext(), "Add a game component to create a game!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            String name = nameGameEdit.getText().toString();
            nameGameEdit.getText().clear();
            cDB.getCurrentGame().setName(name);
            cDB.saveGame();
            Toast toast = Toast.makeText(this.getContext(), "Game saved!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            populateRecyclerView(view);
        }
    }
}