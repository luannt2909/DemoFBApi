package com.luannt.lap10515.demosimpleapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.luannt.lap10515.demosimpleapp.application.MainApplication;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;
import com.luannt.lap10515.demosimpleapp.repository.FacebookApiRepo;
import com.luannt.lap10515.demosimpleapp.utils.AppConstants;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lap10515 on 27/07/2017.
 */

public class MainService extends Service {
    @Inject
    public FacebookApiRepo mApi;

    @Inject
    public Context mContext;

    private String mPageAfter = "";
    private AccessToken token = AccessToken.getCurrentAccessToken();

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.getInstance().getmAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Observable.interval(5, TimeUnit.MINUTES)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        updateFriendListDatabase("");
                    }
                });

        return START_STICKY;
    }
    private void updateFriendListDatabase(String after){
        Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        mApi.getFriendListToUpdateDb(token.getUserId(), token.getToken(), AppConstants.MAX_SIZE, after)
                .subscribeOn(scheduler)
                .retry(3)
                .filter(new Predicate<FriendResponse>() {
                    @Override
                    public boolean test(@Nullable FriendResponse friendResponse) throws Exception {
                        return friendResponse.paging.cursors != null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FriendResponse>() {
                    @Override
                    public void accept(@Nullable FriendResponse friendResponse) throws Exception {
                        for (Friend friend : friendResponse.friendList) {
                            Log.i("NAME", friend.getName());
                        }
                        if (friendResponse.paging.cursors == null) {
                            return;
                        }
                        mPageAfter = friendResponse.paging.cursors.after;
                        Log.i("NAME", mPageAfter);

                        updateFriendListDatabase(mPageAfter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        return;
                    }
                });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
