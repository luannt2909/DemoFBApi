package com.luannt.lap10515.demosimpleapp.view.friendlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.luannt.lap10515.demosimpleapp.R;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.presenter.friendlist.FriendListPresenter;
import com.luannt.lap10515.demosimpleapp.view.base.MainActivity;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class FriendListActivity extends MainActivity<FriendListPresenter> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFriendListFragment();
    }

    protected void addFriendListFragment(){
        Fragment fragment = new FriendListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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

}
