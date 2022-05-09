package dk.itu.gamecreator.android.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import dk.itu.gamecreator.android.Activities.CreateActivity;
import dk.itu.gamecreator.android.Activities.GameActivity;
import dk.itu.gamecreator.android.Activities.MainActivity;
import dk.itu.gamecreator.android.Activities.PlayActivity;
import dk.itu.gamecreator.android.ComponentDB;
import dk.itu.gamecreator.android.R;

public class GameFinishedFragmentPreview extends Fragment {

    // For possible future use - so the success / failure text can be changed
    private TextView successText;
    private Button finishGameButton;
    private Button playAgainButton;

    private Fragment previewFragment = new GameFragment(R.id.create_fragment, true);
    private Fragment editorFragment = new EditorFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_finished, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        successText = view.findViewById(R.id.success_text);
        finishGameButton = view.findViewById(R.id.finish_game_button);
        playAgainButton = view.findViewById(R.id.play_again_button);

        finishGameButton.setVisibility(View.GONE);

        successText.setText("Congratulations! " +
                "You completed the game!");

        playAgainButton.setOnClickListener(v -> playAgain());
    }

    public void playAgain() {
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.create_fragment, previewFragment)
                .commit();
    }

}
