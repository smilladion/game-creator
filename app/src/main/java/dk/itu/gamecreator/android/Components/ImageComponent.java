package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageComponent extends GameComponent {

    private ImageView image;
    private Bitmap bitmap;

    public ImageComponent(int id, Bitmap bitmap) {
        super(id);
        this.bitmap = bitmap;
    }

    public View getView(Context context) {
        image = new ImageView(context);
        image.setLayoutParams(new LinearLayout.LayoutParams(650, 600));
        image.setImageBitmap(bitmap);
        return image;
    }

    @Override
    public String getGravity() {
        return "center";
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
