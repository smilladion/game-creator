package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.itu.gamecreator.android.Adapters.ExpandableListAdapter;
import dk.itu.gamecreator.android.Adapters.RecyclerViewAdapter;
import dk.itu.gamecreator.android.Adapters.StageRecycler;
import dk.itu.gamecreator.android.ClassFinder;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Adapters.ItemMoveCallback;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Dialogs.GameNameDialog;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Stage;

public class EditorFragment extends Fragment {

    ComponentDB cDB;
    StageRecycler adapter;
    RecyclerView recyclerView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> stageNames = new ArrayList<>();
    HashMap<Stage, List<Component>> map = new HashMap<>();

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
        recyclerView = view.findViewById(R.id.current_stages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        expandableListView = view.findViewById(R.id.expandableListView);
        for (Stage s : cDB.getCurrentGame().getStages()) {
            stageNames.add(s.getName());
            map.put(s, s.getGameComponents());
        }
        expandableListAdapter = new ExpandableListAdapter(getContext(), stageNames, map);
        expandableListView.setAdapter(expandableListAdapter);

        saveGame = view.findViewById(R.id.save_game_button);
        saveGame.setOnClickListener(this::saveGame);

        Button addStageButton = view.findViewById(R.id.add_stage);
        addStageButton.setOnClickListener(this::newStage);

        populateRecyclerView();
    }

    public void newStage(View view) {
        Stage stage = new Stage();
        cDB.setCurrentStage(stage);
        cDB.getCurrentGame().addStage(stage);
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

    @Override
    public void onResume() {
        System.out.println("HEY");
        super.onResume();
        populateRecyclerView();
    }

    public void populateRecyclerView() {
        if (cDB.getCurrentGame() == null) {
            cDB.newGame();
        }

        for (Stage s : cDB.getCurrentGame().getStages()) {
            stageNames.add(s.getName());
            map.put(s, s.getGameComponents());
        }
        expandableListAdapter = new ExpandableListAdapter(getContext(), stageNames, map);
        expandableListView.setAdapter(expandableListAdapter);

        adapter = new StageRecycler(this.getContext(), cDB.getCurrentGame().getStages(), getParentFragmentManager());
        /*
        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
*/
        recyclerView.setAdapter(adapter);
    }
}
