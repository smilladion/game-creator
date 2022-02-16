package dk.itu.gamecreator.android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.GameComponent;
import dk.itu.gamecreator.android.R;

public class EditorFragment extends Fragment {

    ComponentDB cDB;
    LinearLayout ll;

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
        for (GameComponent gc: cDB.getCurrentGame().getComponents()) {
            ll.addView(gc.getView(this.getContext()));
        }
        Component c = cDB.getCurrentGame().getSolution();
        if (c != null) {
            ll.addView(c.getView(this.getContext()));
        }
    }
}
