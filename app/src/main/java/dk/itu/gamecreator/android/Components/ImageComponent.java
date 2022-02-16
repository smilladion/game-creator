package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import dk.itu.gamecreator.android.Components.GameComponent;

public class ImageComponent extends GameComponent {

    private ImageView image;

    public ImageComponent(int id, ImageView image) {
        super(id);
        this.image = image;
    }

    public View getView(Context context) {
        return image;
    }
}
