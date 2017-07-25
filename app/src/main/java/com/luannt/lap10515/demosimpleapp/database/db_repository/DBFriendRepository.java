package com.luannt.lap10515.demosimpleapp.database.db_repository;

import android.util.Log;

import com.luannt.lap10515.demosimpleapp.data.db_entity.FriendEntity;
import com.luannt.lap10515.demosimpleapp.database.dao.DaoSession;
import com.luannt.lap10515.demosimpleapp.database.dao.FriendDao;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created by lap10515 on 21/07/2017.
 */

public class DBFriendRepository {
    private final DaoSession daoSession;

    public DBFriendRepository(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public void saveAll(List<FriendEntity> incomes) {
        /*for(FriendEntity entity: incomes){
            QueryBuilder<FriendEntity> origin = daoSession.getFriendDao().queryBuilder()
                                                .where(FriendDao.Properties.FriendId.notEq(entity.getFriendFBId()));
            this.daoSession.getFriendDao().save(origin.uniqueOrThrow());
        }*/
        //daoSession.getFriendDao().save();
        //this.daoSession.getFriendDao().deleteAll();

        //daoSession.getFriendDao().insert(new FriendEntity("luan","luan","luan"));
        //this.daoSession.getFriendDao().insertOrReplaceInTx(incomes,false);
        this.daoSession.getFriendDao().insertOrReplaceInTx(incomes);
        long count = daoSession.getFriendDao().queryBuilder().distinct().orderAsc(FriendDao.Properties.Name).list().size();
        Log.d("COUNT FRIEND", count+"");
    }

    public void deleteAll() {
        this.daoSession.getFriendDao().deleteAll();
    }

    public Observable<List<FriendEntity>> getAll(final int nextPageNumber) {
        //final RxQuery<FriendEntity> query = this.daoSession.getFriendDao().queryBuilder().rx();
        return fromCallable(new Callable<List<FriendEntity>>() {
            @Override
            public List<FriendEntity> call() throws Exception {

                return daoSession.getFriendDao().queryBuilder()
                        .distinct()
                        .limit(20)
                        .offset(20*nextPageNumber)
                        .orderAsc(FriendDao.Properties.Name)
                        .list();
            }
        });
        //return query.list();
        //return this.daoSession.getFriendDao().loadAll();
    }


    public Observable<List<FriendEntity>> fromCallable(final Callable<List<FriendEntity>> callable) {
        return Observable.defer(new Callable<ObservableSource<List<FriendEntity>>>() {

            @Override
            public ObservableSource<List<FriendEntity>> call() {
                List<FriendEntity> result;
                try {
                    result = callable.call();
                } catch (Exception e) {
                    return Observable.error(e);
                }
                return Observable.just(result);
            }
        });
    }
}