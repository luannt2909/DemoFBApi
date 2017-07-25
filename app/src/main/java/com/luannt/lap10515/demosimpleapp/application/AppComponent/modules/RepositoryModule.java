package com.luannt.lap10515.demosimpleapp.application.AppComponent.modules;

import com.luannt.lap10515.demosimpleapp.repository.FacebookApiRepo;
import com.luannt.lap10515.demosimpleapp.repository.FacebookApiRepoImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lap10515 on 18/07/2017.
 */

@Module
public class RepositoryModule {
    @Provides
    public FacebookApiRepo provideFacebookApiRepo(FacebookApiRepoImpl repo){
        return repo;
    }

}
