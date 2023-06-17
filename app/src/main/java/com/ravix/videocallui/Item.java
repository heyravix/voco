package com.ravix.videocallui;

public class Item {

    private String text;

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    private int imageResId;

    public Item(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}
