package com.luannt.lap10515.demosimpleapp.presenter.friendlist;

import com.luannt.lap10515.demosimpleapp.presenter.base.Presenter;
import com.luannt.lap10515.demosimpleapp.view.friendlist.FriendListView;

/**
 * Created by lap10515 on 18/07/2017.
 */

public interface FriendListPresenter extends Presenter<FriendListView> {
    void getFriendListFB(String userId, String token, String nextPage, int nextPageNumber);
    void getFriendByName(String name, int page);
    void refreshList();
}
