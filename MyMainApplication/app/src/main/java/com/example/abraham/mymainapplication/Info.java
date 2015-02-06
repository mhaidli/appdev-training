package com.example.abraham.mymainapplication;

/**
 * Created by Abraham on 1/6/2015.
 */
public class Info {

    private int mImageID;
    private String mText;

    public Info (int imageID, String text){
        mImageID = imageID;
        mText = text;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int imageID) {
        mImageID = imageID;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
