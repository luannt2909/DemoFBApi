package com.luannt.lap10515.demosimpleapp.repository;

import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;

import io.reactivex.Observable;

/**
 * Created by lap10515 on 18/07/2017.
 */

public interface FacebookApiRepo {
    Observable<FriendResponse> getFriendListFB(String userId,
                                               String accessToken,
                                               int limit,
                                               String afterPage,
                                               int nextPageNumber);
}
