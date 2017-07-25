package com.luannt.lap10515.demosimpleapp.presenter.login;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.luannt.lap10515.demosimpleapp.presenter.base.Presenter;
import com.luannt.lap10515.demosimpleapp.view.login.LoginView;

/**
 * Created by lap10515 on 18/07/2017.
 */

public interface LoginPresenter extends Presenter<LoginView> {
    void onLoginSuccessful(LoginResult result);
    void onLoginError(FacebookException error);
    void checkLoginStatus();
}
