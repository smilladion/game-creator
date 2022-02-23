package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.ItemMoveCallback;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.RecyclerViewAdapter;

public class EditorFragment extends Fragment {

    ComponentDB cDB;
    RecyclerViewAdapter adapter;
    Button saveGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();
        if (cDB.getCurrentGame() == null) {
            cDB.newGame();
        }
        saveGame = view.findViewById(R.id.save_game_button);
        saveGame.setOnClickListener(this::saveGame);
        RecyclerView recyclerView = view.findViewById(R.id.current_components);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new RecyclerViewAdapter(this.getContext(), cDB.getCurrentGame().getComponents());

        ItemTouchHelper.Callback callback = new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
    }

    public void saveGame(View view) {
        cDB.saveGame();
    }
}
