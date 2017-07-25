package com.luannt.lap10515.demosimpleapp.view.friendlist;

import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;
import com.luannt.lap10515.demosimpleapp.view.base.MainView;

/**
 * Created by lap10515 on 18/07/2017.
 */

public interface FriendListView extends MainView {
    void updateView(FriendResponse response);
    void disableRefresh();
}
