package com.lishuang.myapp.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lishuang.myapp.dao.Dao;
import com.lishuang.myapp.model.User;

public class UserServiceImpl {


    public Long insertUser(Dao dao, User user) {
        SQLiteDatabase db = dao.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dao.USERNAME, user.getUsername());
        values.put(Dao.PASSWORD, user.getPassword());
        return db.insert(Dao.TABLE1, null, values);
    }

    public boolean login(Dao dao, User user) {
        SQLiteDatabase db = dao.getReadableDatabase();
        String username = user.getUsername();
        String password = user.getPassword();
        // 执行 SQL 查询，以检查输入的用户名和密码是否与数据库中的记录匹配
        Cursor cursor = db.query(Dao.TABLE1, new String[]{"username","password"}, "username=? and password=?", new String[]{username, password}, null, null, null);
        // 检查查询结果是否为空
        return cursor.moveToFirst();
    }

    public boolean exist(Dao dao, String username) {
        SQLiteDatabase db = dao.getReadableDatabase();
        Cursor cursor = db.query(Dao.TABLE1, new String[]{"username"}, "username=?", new String[]{username}, null, null, null);
        return cursor.moveToFirst();
    }

}
