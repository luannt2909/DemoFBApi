package com.luannt.lap10515.demosimpleapp.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.luannt.lap10515.demosimpleapp.data.db_entity.FriendEntity;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * Created by lap10515 on 21/07/2017.
 */


public class FriendDao extends AbstractDao<FriendEntity, String>{

    public static final String TABLENAME = "FRIEND";

    public FriendDao(DaoConfig config) {
        super(config);
    }

    public FriendDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    public static class Properties{
        public static final Property Id = new Property(0, String.class, "id", true, "_id");
        //public static final Property FriendId = new Property(1, String.class, "friend_id", false,"FRIEND_ID");
        public static final Property Name = new Property(1, String.class, "name", false, "NAME");
        public static final Property ImageUrl = new Property(2, String.class, "image_url",false, "IMAGEURL");
    }

    public static void createTable(Database db, boolean ifNotExists){
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FRIEND\" (" + //
                "\"_id\" TEXT PRIMARY KEY ," + // 0: id
                //"\"FRIEND_ID\" TEXT UNIQUE ,"+
                "\"NAME\" TEXT, "+
                "\"IMAGEURL\" TEXT )");
    }

    public static void dropTable(Database db, boolean ifExists){
        String sql ="DROP TABLE "+ (ifExists ? "IF EXISTS " : "") + TABLENAME;
        db.execSQL(sql);
    }
    @Override
    protected FriendEntity readEntity(Cursor cursor, int offset) {
        FriendEntity entity =new FriendEntity(
                //cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0),
                cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0),
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1),
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2)
        );
        return entity;
    }

    @Override
    protected String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, FriendEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        //entity.setFriendFBId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
    }

    @Override
    protected void bindValues(DatabaseStatement stmt, FriendEntity entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if(id != null){
            stmt.bindString(1, id);
        }

        /*String friendId = entity.getFriendFBId();
        if(friendId != null){
            stmt.bindString(2,friendId);
        }*/

        String name = entity.getName();
        if(name != null){
            stmt.bindString(2, name);
        }

        String imageUrl = entity.getUrl();
        if(imageUrl != null){
            stmt.bindString(3, imageUrl);
        }
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, FriendEntity entity) {
        stmt.clearBindings();

        String id = entity.getId();
        if(id != null){
            stmt.bindString(1, id);
        }

        /*String friendId = entity.getFriendFBId();
        if(friendId != null){
            stmt.bindString(2,friendId);
        }*/

        String name = entity.getName();
        if(name != null){
            stmt.bindString(2, name);
        }

        String imageUrl = entity.getUrl();
        if(imageUrl != null){
            stmt.bindString(3, imageUrl);
        }
    }

    @Override
    protected String updateKeyAfterInsert(FriendEntity entity, long rowId) {
        //entity.setId(String.valueOf(rowId));
        return entity.getId();
    }

    @Override
    protected String getKey(FriendEntity entity) {
        return entity !=null ? entity.getId() : null ;
    }

    @Override
    protected boolean hasKey(FriendEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
