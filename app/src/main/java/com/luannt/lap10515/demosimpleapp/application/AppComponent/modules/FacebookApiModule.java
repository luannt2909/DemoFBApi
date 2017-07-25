package com.luannt.lap10515.demosimpleapp.application.AppComponent.modules;

import com.luannt.lap10515.demosimpleapp.api.FacebookApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by lap10515 on 18/07/2017.
 */

@Module
public class FacebookApiModule {
    @Provides
    @Singleton
    public FacebookApi provideFacebookApiModule(Retrofit retrofit){
        return retrofit.create(FacebookApi.class);
    }
}
