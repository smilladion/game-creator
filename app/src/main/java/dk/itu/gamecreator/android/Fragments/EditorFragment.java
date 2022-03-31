package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dk.itu.gamecreator.android.Adapters.RecyclerViewAdapter;
import dk.itu.gamecreator.android.ClassFinder;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Adapters.ItemMoveCallback;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Dialogs.GameNameDialog;
import dk.itu.gamecreator.android.R;

public class EditorFragment extends Fragment {

    ComponentDB cDB;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    Button saveGame;

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

        saveGame = view.findViewById(R.id.save_game_button);
        saveGame.setOnClickListener(this::saveGame);

        Button addComponentButton = view.findViewById(R.id.add_component);
        ListPopupWindow listPopupWindow = new ListPopupWindow(view.getContext(), null,
                com.google.android.material.R.attr.listPopupWindowStyle);

        // Set button as the list popup's anchor
        listPopupWindow.setAnchorView(addComponentButton);

        // Get component class names using the ClassFinder
        ArrayList<Class<?>> classes = new ArrayList<>(ClassFinder.load());
        ArrayList<String> componentTypes = new ArrayList<>();

        for (Class<?> clazz : classes) {
            String name = clazz.getSimpleName();
            String nameFormatted = name.replace("Component", "")
                    .replaceAll("(?=[A-Z])", " ").trim();
            componentTypes.add(nameFormatted);
        }

        // Set list popup's content
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_popup_window_item, componentTypes);
        listPopupWindow.setAdapter(adapter);

        // Respond to list popup window item click
        listPopupWindow.setOnItemClickListener((parent, v, position, id) -> {

            Class<?> clazz = classes.get(position);

            try {
                Component component = (Component) clazz.getConstructor(int.class).newInstance(cDB.getNextComponentId());
                cDB.setComponent(component);

                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().setReorderingAllowed(true)
                        .replace(R.id.create_fragment, new CreateComponentFragment(), null)
                        .addToBackStack(null)
                        .commit();
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }

            // Dismiss popup
            listPopupWindow.dismiss();
        });

        // Show list popup window on button click.
        addComponentButton.setOnClickListener(view1 -> listPopupWindow.show());

        populateRecyclerView();
    }

    public void saveGame(View view) {
        if (cDB.getCurrentGame().getComponents().isEmpty()) {
            Toast toast = Toast.makeText(this.getContext(), "Add a game component to create a game!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (cDB.getCurrentGame().getName() == null || cDB.getCurrentGame().getName().trim().equals("")) {
            GameNameDialog.getDialog(this.getContext());
        } else {
            cDB.saveGame();
            cDB.newGame();

            this.getActivity().finish();
            //finish();

            Toast toast = Toast.makeText(this.getContext(), "Game saved!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void populateRecyclerView() {
        if (cDB.getCurrentGame() == null) {
            cDB.newGame();
        }

        adapter = new RecyclerViewAdapter(this.getContext(), cDB.getCurrentGame().getComponents());

        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
    }
}
