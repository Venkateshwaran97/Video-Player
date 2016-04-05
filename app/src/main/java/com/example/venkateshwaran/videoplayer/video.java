package com.example.venkateshwaran.videoplayer;


import java.io.Serializable;

public class video implements Serializable{
    private long id;
    private String title;
    public video(long songID, String songTitle) {
        id=songID;
        title=songTitle;

    }
    public long getID(){return id;}
    public String getTitle(){return title;}

}