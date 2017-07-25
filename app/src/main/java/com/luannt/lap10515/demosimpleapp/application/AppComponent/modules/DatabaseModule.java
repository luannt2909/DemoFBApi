package com.luannt.lap10515.demosimpleapp.application.AppComponent.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.luannt.lap10515.demosimpleapp.database.dao.DaoMaster;
import com.luannt.lap10515.demosimpleapp.database.dao.DaoSession;
import com.luannt.lap10515.demosimpleapp.database.db_repository.DBFriendRepository;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lap10515 on 20/07/2017.
 */
@Module
public class DatabaseModule {

    @Provides
    public DaoSession provideDaoSession(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"friends-db");
        Database db =helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }
    @Provides
    public DBFriendRepository provideDbFriendRepository(DaoSession daoSession){
        return new DBFriendRepository(daoSession);
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
