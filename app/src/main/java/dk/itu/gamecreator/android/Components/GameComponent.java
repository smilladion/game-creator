package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

import dk.itu.gamecreator.android.Game;

public abstract class GameComponent implements Component {

    private final int id;

    public GameComponent(int id) {
        this.id = id;
    }

    @Override
    public boolean isSolutionComponent() {
        return false;
    }

    public abstract View getDisplayView(Context context);

    public abstract View getCreateView(Context context);

    public abstract boolean saveComponent(Context context);

    public abstract String getName();
}
