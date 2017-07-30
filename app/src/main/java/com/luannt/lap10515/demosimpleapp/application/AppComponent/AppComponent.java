package com.luannt.lap10515.demosimpleapp.application.AppComponent;

import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.AppModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.DatabaseModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.FacebookApiModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.PresenterModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.RepositoryModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.RetrofitModule;
import com.luannt.lap10515.demosimpleapp.service.MainService;
import com.luannt.lap10515.demosimpleapp.view.friendlist.FriendListActivity;
import com.luannt.lap10515.demosimpleapp.view.friendlist.FriendListFragment;
import com.luannt.lap10515.demosimpleapp.view.login.LoginActivity;
import com.luannt.lap10515.demosimpleapp.view.login.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lap10515 on 18/07/2017.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        FacebookApiModule.class,
        RetrofitModule.class,
        DatabaseModule.class,
        PresenterModule.class,
        RepositoryModule.class,
})
public interface AppComponent {
    void inject(LoginActivity loginActivity);
    void inject(FriendListActivity friendListActivity);
    void inject(LoginFragment loginFragment);
    void inject(FriendListFragment friendListFragment);

    //inject Service
    void inject(MainService mainService);

}
