package com.luannt.lap10515.demosimpleapp.api;

import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lap10515 on 18/07/2017.
 */

public interface FacebookApi {
    @GET("v2.10/{user_id}/taggable_friends")
    Observable<FriendResponse> getFbFriendList(
            @Path("user_id") String userId,
            @Query("access_token") String accessToken,
            @Query("limit") int limit,
            @Query("after") String pageAfter);

}
