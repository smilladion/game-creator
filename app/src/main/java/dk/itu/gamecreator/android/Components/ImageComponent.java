package dk.itu.gamecreator.android.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import dk.itu.gamecreator.android.Components.GameComponent;

public class ImageComponent extends GameComponent {

    private ImageView image;
    private Bitmap bitmap;

    private float rotation;

    private int width;
    private int height;

    public ImageComponent(int id, Bitmap bitmap, float rotation, int width, int height) {
        super(id);
        this.bitmap = bitmap;
        this.rotation = rotation;
        this.width = width;
        this.height = height;
    }

    public View getView(Context context) {
        image = new ImageView(context);
        image.setImageBitmap(bitmap);
        image.setMaxWidth(300);
        image.setMaxHeight(300);
        image.setRotation(rotation);
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


    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
