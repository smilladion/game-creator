package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;

import dk.itu.gamecreator.android.Components.Component;

public abstract class SolutionComponent extends Component {

    public SolutionComponent(int id) {
        super(id);
    }

    public abstract View getView(Context context);
}
