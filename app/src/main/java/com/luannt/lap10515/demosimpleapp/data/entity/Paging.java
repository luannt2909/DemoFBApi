package com.luannt.lap10515.demosimpleapp.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lap10515 on 20/07/2017.
 */

public class Paging {

    @SerializedName("cursors")
    public Cursors cursors;

    @SerializedName("next")
    public String next;

    public static class Cursors{
        @SerializedName("before")
        public String before;

        @SerializedName("after")
        public String after;
    }
}
