package com.example.scan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.scan.entity.UserInfo;
import com.example.scan.sqlutil.DBOpenHelper;

public class UserInfoDAOImpl {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public UserInfoDAOImpl(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context, "stock.db", null, 4);
    }

    //注册
    public void registerUser(UserInfo userInfo){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into s_userInfo (userName,password,createDate) values(?,?,datetime('now','localtime'))",
                new Object[]{userInfo.getUserName(),userInfo.getPassword()});
        db.close();
    }

    //登录
    public boolean loginUser(String userName,String password){
        boolean flag = false;
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from s_userInfo where userName = ? and password = ?", new String[] { userName, password});
        if (cursor.moveToFirst()) {// 依次取出数据
            flag = true;
        }
        db.close();
        return flag;
    }
    //查询用户名是否已存在
    public boolean checkUserNameExist(String userName) {
        boolean flag = false;
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from s_userInfo where userName=?", new String[] { userName });
        if (cursor.moveToFirst()) {// 依次取出数据
            flag = true;
        }
        db.close();
        return flag;
    }
}
