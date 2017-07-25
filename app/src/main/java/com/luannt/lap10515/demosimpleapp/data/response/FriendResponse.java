package com.luannt.lap10515.demosimpleapp.data.response;

import com.google.gson.annotations.SerializedName;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.entity.Paging;

import java.util.List;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class FriendResponse {
    @SerializedName("data")
    public List<Friend> friendList;

    @SerializedName("paging")
    public Paging paging;

}
