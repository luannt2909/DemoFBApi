package com.luannt.lap10515.demosimpleapp.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class Friend {
    @SerializedName("id")
    private String friendId;

    @SerializedName("name")
    private String name;


    @SerializedName("picture")
    private Picture picture;


    public Friend( String friendId, String name, Picture picture) {
        this.friendId = friendId;
        this.name = name;
        this.picture = picture;
    }


    public Friend() {
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
