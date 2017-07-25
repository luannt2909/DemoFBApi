package com.luannt.lap10515.demosimpleapp.database.dao;

import com.luannt.lap10515.demosimpleapp.data.db_entity.FriendEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

/**
 * Created by lap10515 on 21/07/2017.
 */

public class DaoSession extends AbstractDaoSession {
    private  final DaoConfig mDaoConfig;
    private final FriendDao mFriendDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);
        mDaoConfig = daoConfigMap.get(FriendDao.class).clone();
        mDaoConfig.initIdentityScope(type);
        mFriendDao = new FriendDao(mDaoConfig,this);
        registerDao(FriendEntity.class,mFriendDao);

    }
    public void clear() {
        mDaoConfig.clearIdentityScope();
    }

    public FriendDao getFriendDao() {
        return mFriendDao;
    }

}
