package com.bignerdranch.android.gamecreator;

import android.widget.ImageView;

public class ImageComponent extends GameComponent {

    private ImageView image;

    public ImageComponent(int id, ImageView image) {
        super(id);
        this.image = image;
    }
}
