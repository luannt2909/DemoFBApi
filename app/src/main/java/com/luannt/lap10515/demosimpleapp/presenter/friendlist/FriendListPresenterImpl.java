package com.luannt.lap10515.demosimpleapp.presenter.friendlist;

import com.luannt.lap10515.demosimpleapp.data.builder.FriendBuilder;
import com.luannt.lap10515.demosimpleapp.data.db_entity.FriendEntity;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;
import com.luannt.lap10515.demosimpleapp.database.db_repository.DBFriendRepository;
import com.luannt.lap10515.demosimpleapp.presenter.base.MainPresenter;
import com.luannt.lap10515.demosimpleapp.repository.FacebookApiRepo;
import com.luannt.lap10515.demosimpleapp.repository.FacebookApiRepoImpl;
import com.luannt.lap10515.demosimpleapp.utils.AppConstants;
import com.luannt.lap10515.demosimpleapp.view.friendlist.FriendListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class FriendListPresenterImpl extends MainPresenter<FriendListView> implements FriendListPresenter {

    FacebookApiRepo mFacebookApiRepo;
    DBFriendRepository mDbFriendRepository;



    @Inject
    public FriendListPresenterImpl(FacebookApiRepoImpl facebookApiRepo, DBFriendRepository dbFriendRepository) {
        mFacebookApiRepo = facebookApiRepo;
        mDbFriendRepository = dbFriendRepository;
        //mDbFriendRepository.deleteAll();
    }

    @Override
    public void getFriendListFB(String userId, String token, String nextPage, int nextPageNumber) {
        mFacebookApiRepo.getFriendListFB(userId, token, AppConstants.MAX_SIZE, nextPage, nextPageNumber)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<FriendResponse>() {
                    @Override
                    public void accept(@NonNull FriendResponse friendResponse) throws Exception {
                        List<FriendEntity> friendEntities = new ArrayList<FriendEntity>();
                        for(Friend friend: friendResponse.friendList){
                            FriendBuilder builder = new FriendBuilder();
                            friendEntities.add(builder.dbBuilber(friend));
                        }
                        mDbFriendRepository.saveAll(friendEntities);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull FriendResponse response) {
                        mView.updateView(response);
                        //List<FriendEntity> result = mDbFriendRepository.getAll();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.disableRefresh();
                        mView.showToast(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        mView.disableRefresh();
                    }
                });

    }
    @Override
    public void refreshList() {

    }

    @Override
    public void setView(FriendListView view) {
        super.setView(view);
    }

    @Override
    public FriendListView getView() {
        return super.getView();
    }
}
