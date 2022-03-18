package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

public abstract class SolutionComponent implements Component {

    private final int id;

    public SolutionComponent(int id) {
        this.id = id;
    }

    public abstract View getDisplayView(Context context);

    public abstract View getCreateView(Context context);

    public abstract String getGravity();
}
