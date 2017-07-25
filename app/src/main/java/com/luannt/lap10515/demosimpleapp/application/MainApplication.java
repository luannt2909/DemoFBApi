package com.luannt.lap10515.demosimpleapp.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.DaggerAppComponent;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.AppModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.DatabaseModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.FacebookApiModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.PresenterModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.RepositoryModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.RetrofitModule;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class MainApplication extends Application {

    private static MainApplication mApplication;
    private AppComponent mAppComponent;
    public static MainApplication getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent= DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule())
                .databaseModule(new DatabaseModule())
                .facebookApiModule(new FacebookApiModule())
                .presenterModule(new PresenterModule())
                .repositoryModule(new RepositoryModule())
                .build();
        mApplication = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public AppComponent getmAppComponent(){
        return mAppComponent;
    }
}
