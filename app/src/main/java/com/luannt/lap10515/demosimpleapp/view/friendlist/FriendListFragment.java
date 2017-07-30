package com.luannt.lap10515.demosimpleapp.view.friendlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.luannt.lap10515.demosimpleapp.R;
import com.luannt.lap10515.demosimpleapp.R2;
import com.luannt.lap10515.demosimpleapp.adapter.FriendListAdapter;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.data.entity.Friend;
import com.luannt.lap10515.demosimpleapp.data.response.FriendResponse;
import com.luannt.lap10515.demosimpleapp.presenter.friendlist.FriendListPresenter;
import com.luannt.lap10515.demosimpleapp.view.base.MainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by lap10515 on 26/07/2017.
 */

public class FriendListFragment extends MainFragment<FriendListPresenter> implements FriendListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R2.id.rcv_friend_list)
    RecyclerView rcv_friendList;

    @BindView(R2.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    SearchView mSearchView;

    private FriendListAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mOnloadmoreRecyclerView;

    private PublishSubject<String> mSearchResultSubject;

    private AccessToken token;
    private List<Friend> mFriendList = new ArrayList<>();
    private String PAGE_AFTER = "";
    public String QUERY="";
    private boolean isSearch = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        token = AccessToken.getCurrentAccessToken();
        mPresenter.getFriendListFB(token.getUserId(), token.getToken(), "",0);
        mSearchResultSubject = PublishSubject.create();
        mSearchResultSubject.debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        QUERY = s;
                        mFriendList.clear();
                        mOnloadmoreRecyclerView.resetState();
                        mPresenter.getFriendByName(s,0);
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item_search = menu.findItem(R.id.menu_searchview);
        mSearchView = new SearchView(getActivity());
        MenuItemCompat.setActionView(item_search, mSearchView);
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSearch = true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearch = false;
                return true;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchResultSubject.onNext(newText);
                return true;
            }
        });

    }


    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friend_list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                this.getActivity().finish();
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
                if(isSearch){
                    mPresenter.getFriendByName(QUERY, page);
                }else
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
    public void showResultSearch(List<Friend> result) {
        if(!mFriendList.containsAll(result)){
            mFriendList.addAll(result);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mFriendList.clear();
        if(isSearch){
            mPresenter.getFriendByName(QUERY,0);
        }else
            mPresenter.getFriendListFB(token.getUserId(), token.getToken(), "",0);
        mOnloadmoreRecyclerView.resetState();
    }

    @Override
    public void disableRefresh() {
        if(swipeRefreshLayout!=null && swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}
