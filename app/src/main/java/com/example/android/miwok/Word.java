package com.example.android.miwok;

/**
 * Created by sagar on 15/12/17.
 */

public class Word {
    private String mDefaultString;
    private String mMemokString;
    private int imageId;
    private int musicId;

    public Word(String mDefaultString, String mMemokString, int imageId, int musicId) {
        this.mDefaultString = mDefaultString;
        this.mMemokString = mMemokString;
        this.imageId = imageId;
        this.musicId = musicId;
    }

    public Word(String mDefaultString, String mMemokString, int musicId) {
        this.mDefaultString = mDefaultString;
        this.mMemokString = mMemokString;
        this.musicId = musicId;
    }

    public int getImageResouceId() {
        return imageId;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getmDefaultString() {
        return mDefaultString;
    }

    public String getmMemokString() {
        return mMemokString;
    }
}
