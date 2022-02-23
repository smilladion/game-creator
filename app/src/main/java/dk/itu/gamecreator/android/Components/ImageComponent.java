package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import dk.itu.gamecreator.android.Components.GameComponent;

public class ImageComponent extends GameComponent {

    private ImageView image;
    private Bitmap bitmap;

    public ImageComponent(int id, Bitmap bitmap) {
        super(id);
        this.bitmap = bitmap;
    }

    public View getView(Context context) {
        image = new ImageView(context);
        image.setImageBitmap(bitmap);
        image.setMaxWidth(300);
        image.setMaxHeight(300);
        image.setRotation(270);
        return image;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
