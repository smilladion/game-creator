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
import androidx.fragment.app.FragmentManager;

import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.Components.Component;
import dk.itu.gamecreator.android.Components.SolutionComponent;
import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.R;
import dk.itu.gamecreator.android.Stage;

public class GameFragment extends Fragment {

    private LinearLayout gameLayout;
    private ComponentDB cDB;
    private TextView text;
    private Context context;
    private Game game;
    private Stage currentStage;
    private int parentFragmentRef;
    private boolean isPreview;

    public GameFragment(int parentFragmentRef, boolean isPreview) {
        super();
        /**
         * We need to know what fragment to replace when the game finishes since this fragment is used in different places.
         * */
        this.parentFragmentRef = parentFragmentRef;
        this.isPreview = isPreview;
    }

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
        gameLayout = view.findViewById(R.id.game_layout);
        text = view.findViewById(R.id.text);

        if (!game.getStages().isEmpty()) {

            Stage s = game.getStages().get(0);
            currentStage = s;

            for (Component c : s.getGameComponents()) {
                LinearLayout viewLayout = new LinearLayout(context);
                viewLayout.setGravity(Gravity.CENTER);
                View v = c.getDisplayView(context);
                viewLayout.addView(v);
                gameLayout.addView(viewLayout);
            }
            setSolvedListener(s);
        }
    }

    public void setSolvedListener(Stage s) {
        SolutionComponent solutionComponent = s.getSolutionComponent();
        if (solutionComponent != null) {
            solutionComponent.addSolvedListener(new SolutionComponent.SolvedListener() {
                @Override
                public void onChange() {
                    nextStage();
                }
            });
        }
    }

    public void nextStage() {
        Stage s = currentStage.getNextStage();
        currentStage = s;
        if (s != null) {
            gameLayout.removeAllViews();
            for (Component c : s.getGameComponents()) {
                LinearLayout viewLayout = new LinearLayout(context);
                viewLayout.setGravity(Gravity.CENTER);
                View v = c.getDisplayView(context);
                viewLayout.addView(v);
                gameLayout.addView(viewLayout);
            }
            setSolvedListener(s);
        } else {
            endGame();
        }
    }

    public void endGame() {
        FragmentManager fm = getParentFragmentManager();
        Fragment fragment;
        if (isPreview) {
            fragment = new GameFinishedFragmentPreview();
        } else {
            fragment = new GameFinishedFragment();
        }
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(parentFragmentRef, fragment)
                .commit();
    }
}
