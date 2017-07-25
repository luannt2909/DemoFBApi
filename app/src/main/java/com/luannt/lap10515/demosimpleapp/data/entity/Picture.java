package com.luannt.lap10515.demosimpleapp.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap10515 on 20/07/2017.
 */

public class Picture {
    @SerializedName("data")
    private Avatar avatar;

    public Picture(Avatar avatar) {
        this.avatar = avatar;
    }

    public Picture() {
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public static class Avatar{
        @SerializedName("url")
        private String url;

        public Avatar(String url) {
            this.url = url;
        }

        public Avatar() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
