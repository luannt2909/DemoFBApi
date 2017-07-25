package com.luannt.lap10515.demosimpleapp.application.AppComponent.modules;

import com.luannt.lap10515.demosimpleapp.presenter.friendlist.FriendListPresenter;
import com.luannt.lap10515.demosimpleapp.presenter.friendlist.FriendListPresenterImpl;
import com.luannt.lap10515.demosimpleapp.presenter.login.LoginPresenter;
import com.luannt.lap10515.demosimpleapp.presenter.login.LoginPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lap10515 on 18/07/2017.
 */

@Module
public class PresenterModule {
    @Provides
    public LoginPresenter provideLoginPresenter(LoginPresenterImpl presenter){
        return presenter;
    }

    @Provides
    public FriendListPresenter provideFriendListPresenter(FriendListPresenterImpl presenter){
        return presenter;
    }

}
