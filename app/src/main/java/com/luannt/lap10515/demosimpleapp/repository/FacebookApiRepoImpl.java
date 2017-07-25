package com.luannt.lap10515.demosimpleapp.repository;

import android.util.Log;

import com.luannt.lap10515.demosimpleapp.api.FacebookApi;
import com.luannt.lap10515.demosimpleapp.data.builder.FriendBuilder;
import com.luannt.lap10515.demosimpleapp.data.db_entity.FriendEntity;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.entity.Paging;
import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;
import com.luannt.lap10515.demosimpleapp.database.db_repository.DBFriendRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class FacebookApiRepoImpl implements FacebookApiRepo{
    FacebookApi mFacebookApi;
    DBFriendRepository mDbFriendRepo;

    @Inject
    public FacebookApiRepoImpl(FacebookApi mFacebookApi, DBFriendRepository dbFriendRepo) {
        this.mFacebookApi = mFacebookApi;
        this.mDbFriendRepo = dbFriendRepo;
    }

    @Override
    public Observable<FriendResponse> getFriendListFB(String userId, String accessToken, int limit, final String afterPage, int nextPageNumber) {
        Observable<FriendResponse> offlineResponse = mDbFriendRepo.getAll(nextPageNumber)
                .filter(new Predicate<List<FriendEntity>>() {
                    @Override
                    public boolean test(@NonNull List<FriendEntity> friendEntities) throws Exception {
                        return friendEntities.size()>0;
                    }
                })
                .map(new Function<List<FriendEntity>, FriendResponse>() {
                    @Override
                    public FriendResponse apply(@NonNull List<FriendEntity> friendEntities) throws Exception {
                        Log.d("Call Offline","Calling.....");
                        FriendResponse response = new FriendResponse();
                        FriendBuilder builder = new FriendBuilder();
                        List<Friend> friendList = new ArrayList<Friend>();
                        for(FriendEntity entity: friendEntities){
                            friendList.add(builder.builder(entity));
                        }

                        Paging paging = new Paging();
                        Paging.Cursors cursors = new Paging.Cursors();
                        cursors.after = afterPage;
                        paging.cursors= cursors;

                        response.friendList = friendList;
                        response.paging = paging;
                        return response;
                    }
                });
        Observable<FriendResponse> apiResponse = mFacebookApi.getFbFriendList(userId, accessToken, limit, afterPage);
        //return mFacebookApi.getFbFriendList(userId, accessToken, limit, afterPage);
        //return Observable.concat(offlineResponse,apiResponse);
               // .subscribeOn(Schedulers.computation());
        //return offlineResponse;

        return Observable.concat(offlineResponse, apiResponse).take(1);
    }
}
