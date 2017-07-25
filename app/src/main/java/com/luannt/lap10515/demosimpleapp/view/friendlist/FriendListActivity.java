package com.luannt.lap10515.demosimpleapp.view.friendlist;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.luannt.lap10515.demosimpleapp.R;
import com.luannt.lap10515.demosimpleapp.R2;
import com.luannt.lap10515.demosimpleapp.adapter.FriendListAdapter;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;
import com.luannt.lap10515.demosimpleapp.presenter.friendlist.FriendListPresenter;
import com.luannt.lap10515.demosimpleapp.view.base.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class FriendListActivity extends MainActivity<FriendListPresenter> implements FriendListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R2.id.rcv_friend_list)
    RecyclerView rcv_friendList;

    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private FriendListAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mOnloadmoreRecyclerView;

    private AccessToken token;
    private List<Friend> mFriendList = new ArrayList<>();
    //private String mPageAfter="";
    public String PAGE_AFTER = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        token = AccessToken.getCurrentAccessToken();
        mPresenter.getFriendListFB(token.getUserId(), token.getToken(), "",0);
    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark)
        );
        swipeRefreshLayout.setOnRefreshListener(this);

        //LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_friendList.setLayoutManager(layoutManager);
        rcv_friendList.setItemAnimator(new DefaultItemAnimator());
        setOnLoadmore(layoutManager);
        rcv_friendList.setOnScrollListener(mOnloadmoreRecyclerView);

        //Adapter
        mAdapter = new FriendListAdapter(getApplicationContext(),mFriendList);
        rcv_friendList.setAdapter(mAdapter);
    }

    private void setOnLoadmore(LinearLayoutManager layoutManager) {
        mOnloadmoreRecyclerView =new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.getFriendListFB(token.getUserId(), token.getToken(), PAGE_AFTER, page);
            }
        };
    }

    @Override
    public void updateView(FriendResponse response) {
        disableRefresh();

        List<Friend> list = response.friendList;
        if(response.paging.cursors ==null){
            return;
        }
        String after = response.paging.cursors.after;
        if(!PAGE_AFTER.equals(after)){
            PAGE_AFTER = after;
        }
        if(!mFriendList.containsAll(list)){
            mFriendList.addAll(list);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mPresenter.getFriendListFB(token.getUserId(), token.getToken(), "",0);
    }

    @Override
    public void disableRefresh() {
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}
