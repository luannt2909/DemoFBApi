package com.luannt.lap10515.demosimpleapp.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.luannt.lap10515.demosimpleapp.R;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.presenter.login.LoginPresenter;
import com.luannt.lap10515.demosimpleapp.view.base.MainActivity;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class LoginActivity extends MainActivity<LoginPresenter> {

    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLoginFragment();

    }

    protected void addLoginFragment(){
        fragment = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
