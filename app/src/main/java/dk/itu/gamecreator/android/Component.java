package dk.itu.gamecreator.android;

import android.content.Context;
import android.view.View;

public abstract class Component {

    private final int id;

    public Component(int id) {
        this.id = id;
    }

    public abstract View getView(Context context);

}
