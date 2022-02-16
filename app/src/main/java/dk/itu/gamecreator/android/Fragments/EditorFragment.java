package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.Components.TextComponent;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.RecyclerViewAdapter;

public class EditorFragment extends Fragment {

    ComponentDB cDB;
    LinearLayout ll;
    RecyclerViewAdapter adapter;

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
        ll = view.findViewById(R.id.game_component_layout);

        RecyclerView recyclerView = view.findViewById(R.id.currentComponents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ArrayList<String> texts = new ArrayList<>();
        for (GameComponent gc : cDB.getCurrentGame().getComponents()) {
            if (gc instanceof TextComponent) {
                texts.add(((TextComponent) gc).getText());
            }
        }

        adapter = new RecyclerViewAdapter(this.getContext(), texts);
        recyclerView.setAdapter(adapter);

        /* for (GameComponent gc: cDB.getCurrentGame().getComponents()) {
            ll.addView(gc.getView(this.getContext()));
        }
        Component c = cDB.getCurrentGame().getSolution();
        if (c != null) {
            ll.addView(c.getView(this.getContext()));
        } */
    }
}
