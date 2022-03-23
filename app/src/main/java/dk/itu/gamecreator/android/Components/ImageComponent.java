package dk.itu.gamecreator.android.Components;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import dk.itu.gamecreator.android.R;

public class ImageComponent extends GameComponent {

    private ImageView image;
    private Bitmap bitmap;

    public ImageComponent(int id, Bitmap bitmap) {
        super(id);
        this.bitmap = bitmap;
    }

    public View getDisplayView(Context context) {
        image = new ImageView(context);
        image.setLayoutParams(new LinearLayout.LayoutParams(650, 600));
        image.setImageBitmap(bitmap);
        return image;
    }

    @Override
    public View getCreateView(Context context) {
        return (LinearLayout) ((Activity) context).findViewById(R.id.image_layout);
    }

    @Override
    public String getGravity() {
        return "center";
    }

    @Override
    public void saveComponent(Context context) {

    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
