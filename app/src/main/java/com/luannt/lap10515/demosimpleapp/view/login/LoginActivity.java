package com.luannt.lap10515.demosimpleapp.view.login;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.luannt.lap10515.demosimpleapp.R;
import com.luannt.lap10515.demosimpleapp.R2;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.presenter.login.LoginPresenter;
import com.luannt.lap10515.demosimpleapp.view.base.MainActivity;
import com.luannt.lap10515.demosimpleapp.view.friendlist.FriendListActivity;

import butterknife.BindView;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class LoginActivity extends MainActivity<LoginPresenter> implements LoginView {

    @BindView(R2.id.btnLogin)
    LoginButton btnLogin;

    CallbackManager mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFbSDK();
        mPresenter.checkLoginStatus();

    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initFbSDK() {
        mCallback= CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(mContext);

        btnLogin.setReadPermissions("public_profile", "email", "user_friends","user_about_me","user_location","user_location");
        btnLogin.registerCallback(mCallback, fbLoginCallback);
        LoginManager.getInstance().registerCallback(mCallback,fbLoginCallback);
    }

    @Override
    public void loginSuccessful(String id) {
        this.showToast(id);
        startActivity(new Intent(LoginActivity.this, FriendListActivity.class));
    }

    @Override
    public void loginFail() {

    }

    private final FacebookCallback fbLoginCallback = new FacebookCallback<LoginResult>(){

        @Override
        public void onSuccess(LoginResult loginResult) {
           mPresenter.onLoginSuccessful(loginResult);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            mPresenter.onLoginError(error);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallback.onActivityResult(requestCode, resultCode, data);
    }
}
