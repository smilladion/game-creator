package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dk.itu.gamecreator.android.ClassFinder;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Adapters.ItemMoveCallback;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Adapters.RecyclerViewAdapter;

public class EditorFragment extends Fragment {

    ComponentDB cDB;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    Fragment textFragment = new CreateTextFragment();
    Fragment textSolutionFragment = new CreateTextSolutionFragment();
    Fragment imageFragment = new CreateImageFragment();
    Fragment multipleFragment = new CreateMultipleChoiceFragment();

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

        Button addComponentButton = view.findViewById(R.id.add_component);
        ListPopupWindow listPopupWindow = new ListPopupWindow(view.getContext(), null,
                com.google.android.material.R.attr.listPopupWindowStyle);

        // Set button as the list popup's anchor
        listPopupWindow.setAnchorView(addComponentButton);

        // Get component class names using the ClassFinder
        ArrayList<Class<?>> classes = new ArrayList<>(ClassFinder.load());
        ArrayList<String> componentTypes = new ArrayList<>();

        for (Class<?> clazz : classes) {
            componentTypes.add(clazz.getSimpleName());
        }

        // Set list popup's content
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_popup_window_item, componentTypes);
        listPopupWindow.setAdapter(adapter);

        // Respond to list popup window item click
        listPopupWindow.setOnItemClickListener((parent, v, position, id) -> {
            // Create instance from class:
            // clazz.getConstructor(Integer.class, String.class).newInstance(51, "asdasd");
            switch (componentTypes.get(position)) {
                case "TextComponent":
                    openComponentFragment(textFragment);
                    break;
                case "TextSolutionComponent":
                    openComponentFragment(textSolutionFragment);
                    break;
                case "ImageComponent":
                    openComponentFragment(imageFragment);
                    break;
                case "MultipleChoiceComponent":
                    openComponentFragment(multipleFragment);
                    break;
            }

            // Dismiss popup.
            listPopupWindow.dismiss();
        });

        // Show list popup window on button click.
        addComponentButton.setOnClickListener(view1 -> listPopupWindow.show());

        populateRecyclerView();
    }

    public void openComponentFragment(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, fragment, null)
                .addToBackStack(null)
                .commit();
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
