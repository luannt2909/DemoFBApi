package com.luannt.lap10515.demosimpleapp.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.AppComponent;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.DaggerAppComponent;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.AppModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.DatabaseModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.FacebookApiModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.PresenterModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.RepositoryModule;
import com.luannt.lap10515.demosimpleapp.application.AppComponent.modules.RetrofitModule;
import com.luannt.lap10515.demosimpleapp.service.MainService;

/**
 * Created by lap10515 on 18/07/2017.
 */

public class MainApplication extends Application {

    private static MainApplication mApplication;
    private AppComponent mAppComponent;


    protected BroadcastReceiver mNetworkBroadcast;
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
        Fresco.initialize(this);
        Intent intent = new Intent(this, MainService.class);
        startService(intent);
        registerReceiver(mNetworkBroadcast,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Intent intent = new Intent(this, MainService.class);
        stopService(intent);
        unregisterReceiver(mNetworkBroadcast);
    }

    public AppComponent getmAppComponent(){
        return mAppComponent;
    }
}
