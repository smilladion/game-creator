package dk.itu.gamecreator.android;

import android.content.Context;
import android.view.View;

public abstract class GameComponent extends Component {

    public GameComponent(int id) {
        super(id);
    }

    public abstract View getView(Context context);
}
