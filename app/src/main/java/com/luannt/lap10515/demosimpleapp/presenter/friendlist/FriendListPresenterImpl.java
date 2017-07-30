package com.luannt.lap10515.demosimpleapp.presenter.friendlist;

import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;
import com.luannt.lap10515.demosimpleapp.presenter.base.MainPresenter;
import com.luannt.lap10515.demosimpleapp.repository.FacebookApiRepo;
import com.luannt.lap10515.demosimpleapp.repository.FacebookApiRepoImpl;
import com.luannt.lap10515.demosimpleapp.utils.AppConstants;
import com.luannt.lap10515.demosimpleapp.view.friendlist.FriendListView;

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

    @Inject
    public FriendListPresenterImpl(FacebookApiRepoImpl facebookApiRepo) {
        mFacebookApiRepo = facebookApiRepo;
    }

    @Override
    public void getFriendListFB(String userId, String token, String nextPage, int nextPageNumber) {
        mFacebookApiRepo.getFriendListFB(userId, token, AppConstants.MAX_SIZE, nextPage, nextPageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull FriendResponse response) {
                        mView.updateView(response);
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
    public void getFriendByName(String name, int page) {
        mFacebookApiRepo.findFriendByName(name, page)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Friend>>() {
                    @Override
                    public void accept(@NonNull List<Friend> friends) throws Exception {
                        mView.showResultSearch(friends);
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
