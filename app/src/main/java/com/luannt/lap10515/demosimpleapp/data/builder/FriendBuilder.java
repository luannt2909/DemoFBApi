package com.luannt.lap10515.demosimpleapp.data.builder;

import com.luannt.lap10515.demosimpleapp.data.db_entity.FriendEntity;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.entity.Picture;

/**
 * Created by lap10515 on 24/07/2017.
 */

public class FriendBuilder extends BaseBuilder<FriendEntity, Friend> {
    @Override
    public Friend builder(FriendEntity friendEntity) {
        Friend friend =new Friend();
        friend.setFriendId(friendEntity.getId());
        friend.setName(friendEntity.getName());

        Picture.Avatar avatar =new Picture.Avatar();
        Picture picture =new Picture();

        avatar.setUrl(friendEntity.getUrl());
        picture.setAvatar(avatar);

        friend.setPicture(picture);
        return friend;
    }

    @Override
    public FriendEntity dbBuilber(Friend friend) {
        FriendEntity entity = new FriendEntity();
        entity.setId(friend.getFriendId());
        entity.setName(friend.getName());
        entity.setUrl(friend.getPicture().getAvatar().getUrl());
        return entity;
    }
}
