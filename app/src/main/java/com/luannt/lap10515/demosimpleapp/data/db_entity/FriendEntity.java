package com.luannt.lap10515.demosimpleapp.data.db_entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by lap10515 on 21/07/2017.
 */
@Entity
public class FriendEntity {

    @Id
    private String id;

    private String name;

    private String url;
    @Generated(hash = 1420979857)
    public FriendEntity(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Generated(hash = 834006476)
    public FriendEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public String getFriendFBId() {
        return friendFBId;
    }

    public void setFriendFBId(String friendFBId) {
        this.friendFBId = friendFBId;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
