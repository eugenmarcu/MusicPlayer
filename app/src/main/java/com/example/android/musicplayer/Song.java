package com.example.android.musicplayer;

import android.media.Image;

/**
 * Created by Eugen on 22-Feb-18.
 */

public class Song {
    private String mTitle;
    private String mArtist;
    private String mDuration;
    private int mRanking;
    private boolean mIsFavorite;

    public Song(String title, String artist, String duration, int rank, boolean favorite) {
        this.mTitle = title;
        this.mArtist = artist;
        this.mDuration = duration;
        this.mRanking = rank;
        this.mIsFavorite = favorite;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getArtist() {
        return this.mArtist;
    }


    public String getDuration(){
        return this.mDuration;
    }

    public int getRanking(){
        return this.mRanking;
    }

    public boolean isFavorite(){
        return this.mIsFavorite;
    }
    public void setFavorite(boolean value){
        this.mIsFavorite = value;
    }

}
