package dk.itu.gamecreator.android;

import android.content.Context;
import android.view.View;

public abstract class SolutionComponent extends Component {

    public SolutionComponent(int id) {
        super(id);
    }

    public abstract View getView(Context context);
}
