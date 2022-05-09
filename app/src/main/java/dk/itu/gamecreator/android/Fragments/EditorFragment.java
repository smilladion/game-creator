package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.itu.gamecreator.android.Adapters.ExpandableListAdapter;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Dialogs.GameNameDialog;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Adapters.RecyclerViewAdapter;
import dk.itu.gamecreator.android.Util;
import dk.itu.gamecreator.android.Stage;

public class EditorFragment extends Fragment {

    private ComponentDB cDB;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<Stage> stages = new ArrayList<>();
    private HashMap<String, List<Component>> map = new HashMap<>();


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

        Util.requestCurrentLocation(this.getContext(), location -> {
            cDB.getCurrentGame().setLocation(location);
        });

        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        expandableListView = view.findViewById(R.id.expandableListView);

        Button addStageButton = view.findViewById(R.id.add_stage);
        addStageButton.setOnClickListener(this::newStage);

        populateExpandableListView();
    }

    public void newStage(View view) {
        Stage stage = new Stage();
        stage.setId(cDB.getNextStageID());
        if (cDB.getCurrentStage() != null) {
            cDB.getCurrentStage().setNextStage(stage);
        }
        cDB.setCurrentStage(stage);
        cDB.getCurrentGame().addStage(stage);
        populateExpandableListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateExpandableListView();
    }

    public void populateExpandableListView() {
        if (cDB.getCurrentGame() == null) {
            cDB.newGame();
        }

        stages.clear();
        for (Stage s : cDB.getCurrentGame().getStages()) {
            stages.add(s);
            map.put(s.getName(), s.getGameComponents());
        }
        expandableListAdapter = new ExpandableListAdapter(getContext(), stages, map, getParentFragmentManager());
        expandableListView.setAdapter(expandableListAdapter);

        // Possibly implement the item touch call back - see old version of app for implementation.
    }
}
