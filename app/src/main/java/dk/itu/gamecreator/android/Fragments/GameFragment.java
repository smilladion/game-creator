package dk.itu.gamecreator.android.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;

public class GameFragment extends Fragment {

    LinearLayout ll;
    ComponentDB cDB;
    TextView text;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cDB = ComponentDB.getInstance();
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ll = view.findViewById(R.id.game_layout);
        text = view.findViewById(R.id.text);
        if (cDB.getCurrentGame().getComponents().isEmpty()) {
            text.setText("Nothing to show");
        } else {
            for (Component c: cDB.getCurrentGame().getComponents()) {
                ll.addView(c.getView(context));
            }
        }
    }
}
