package com.example.tugasbesar_03;

import android.widget.ImageView;

public class Comic {
    protected ImageView comicImage;
    protected String comicName;

    public Comic(ImageView comicImage, String comicName){
        this.comicImage = comicImage;
        this.comicName = comicName;
    }

    public ImageView getComicImage() {
        return comicImage;
    }

    public void setComicImage(ImageView comicImage) {
        this.comicImage = comicImage;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }
}
