package com.mrs.rainbow7music;

import android.graphics.Bitmap;

import java.io.Serializable;


public class Song implements Serializable {

    /**
     * Details of song object
     */
    private static final long serialVersionUID = 1L;
    private long msongID;
    private String msongTitle;
    private String msongData;
    private Bitmap msongimageView;
    private String mSongArtist;

    //constructor to set id,title etc..

    public Song(long songID, String songTitle, String songData, String songArtist,Bitmap imageView) {

        this.msongID = songID;
        this.msongTitle = songTitle;
        this.msongData = songData;
        this.msongimageView = imageView;
        this.mSongArtist = songArtist;
    }


    //getters and setters of song

    public String getmSongArtist() {
        return mSongArtist;
    }

    public void setmSongArtist(String mSongArtist) {
        this.mSongArtist = mSongArtist;
    }

    public long getMsongID() {
        return msongID;
    }

    public void setMsongID(long msongID) {
        this.msongID = msongID;
    }

    public String getMsongTitle() {
        return msongTitle;
    }

    public void setMsongTitle(String msongTitle) {
        this.msongTitle = msongTitle;
    }

    public String getMsongData() {
        return msongData;
    }

    public void setMsongData(String msongData) {
        this.msongData = msongData;
    }

    public Bitmap getMsongimageView() {
        return msongimageView;
    }

    public void setMsongimageView(Bitmap msongimageView) {
        this.msongimageView = msongimageView;
    }

}