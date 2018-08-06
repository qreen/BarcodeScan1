package com.example.scan.sqlutil;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by T-Baymax on 2016/12/15.
 */

public class DBOpenHelper  extends SQLiteOpenHelper {
    /**
     *
     * @param context 上下文
     * @param name 数据库名
     * @param factory 可选的数据库游标工厂类，当查询(query)被提交时，该对象会被调用来实例化一个游标。默认为null。
     * @param version 数据库版本号
     */
    public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    // 覆写onCreate方法，当数据库创建时就用SQL命令创建一个表
    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户表
        db.execSQL("create table if not exists s_userInfo(id integer primary key autoincrement," +
                    "userName varchar(200) unique, password varchar(200),createDate DATETIME )");
        //参数表
        db.execSQL("create table if not exists s_paramentSetting(id integer primary key autoincrement," +
                "numEnableInput integer, inexistEnableAdd integer,aginCheck integer,createDate DATETIME,modifierDate DATETIME )");
        //接口过来的数据表
        db.execSQL("create table if not exists s_scanErpData(id integer primary key autoincrement," +
                "serialNum varchar(200),commodityCode varchar(200),commodityNum int,createDate DATETIME)");
        //扫描字表
        db.execSQL("create table if not exists s_scanSublist(id integer primary key autoincrement," +
                "serialNum varchar(200),goodsAllocation varchar(200),remark varchar(200)," +
                "commodityCode varchar(200),commodityNum int, createDate DATETIME,modifierDate DATETIME)");

        //插入默认参数数据
        db.execSQL("insert into s_paramentSetting (numEnableInput,inexistEnableAdd,aginCheck,createDate,modifierDate) values(1,1,1,datetime('now','localtime'),datetime('now','localtime'))");
        //插入管理员
        db.execSQL("insert into s_userInfo (userName,password,createDate) values('admin','admin',datetime('now','localtime'))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS s_paramentSetting");
        db.execSQL("DROP TABLE IF EXISTS s_scanErpData");
        db.execSQL("DROP TABLE IF EXISTS s_scanSublist");
        db.execSQL("DROP TABLE IF EXISTS s_userInfo");
        onCreate(db);
    }
}
