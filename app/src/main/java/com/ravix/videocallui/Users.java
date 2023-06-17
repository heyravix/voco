package com.ravix.videocallui;

public class Users {
    private int imageDrawable;
    private String name;

    public Users(int imageDrawable, String name) {
        this.imageDrawable = imageDrawable;
        this.name = name;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public String getName() {
        return name;
    }
}
