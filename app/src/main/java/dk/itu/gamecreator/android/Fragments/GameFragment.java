package dk.itu.gamecreator.android.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Stage;

public class GameFragment extends Fragment {

    LinearLayout ll;
    ComponentDB cDB;
    TextView text;
    Context context;
    Game game;
    Stage currentStage;

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
        game = cDB.getCurrentGame();

        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ll = view.findViewById(R.id.game_layout);
        text = view.findViewById(R.id.text);

        final Observer<Stage> stageObserver = new Observer<Stage>() {
            @Override
            public void onChanged(Stage stage) {
                nextStage();
            }
        };

        game.getcStage().observe(this.getViewLifecycleOwner(), stageObserver);

        Stage s = game.getStages().get(0);
        currentStage = s;

        for (Component c: s.getGameComponents()) {
            LinearLayout viewLayout = new LinearLayout(context);
            View v = c.getDisplayView(context);
            viewLayout.addView(v);
            ll.addView(viewLayout);
        }
        /*
        for (Stage s : game.getStages()) {
            for (Component c: s.getGameComponents()) {
                LinearLayout viewLayout = new LinearLayout(context);
                View v = c.getDisplayView(context);
            /*String gravity = c.getGravity();
            if (gravity.equals("left")) {
                viewLayout.setGravity(Gravity.LEFT);
            } else if (gravity.equals("right")) {
                viewLayout.setGravity(Gravity.RIGHT);
            } else {
                viewLayout.setGravity(Gravity.CENTER);
            }
                viewLayout.addView(v);
                ll.addView(viewLayout); */
    }

    public void nextStage() {
        Stage s = currentStage.getNextStage();
        for (Component c: s.getGameComponents()) {
            LinearLayout viewLayout = new LinearLayout(context);
            View v = c.getDisplayView(context);
            viewLayout.addView(v);
            ll.removeAllViews();
            ll.addView(viewLayout);
        }
    }
}
