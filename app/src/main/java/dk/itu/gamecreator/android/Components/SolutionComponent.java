package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

import dk.itu.gamecreator.android.Game;
import dk.itu.gamecreator.android.Stage;

public abstract class SolutionComponent implements Component {

    private final int id;
    private Stage stage;
    private Game game;

    public SolutionComponent(int id) {
        this.id = id;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public abstract View getDisplayView(Context context);

    public abstract View getCreateView(Context context);

    public void onStageDone() {
        game.startNextStage();
    }

    public abstract String getGravity();
}
