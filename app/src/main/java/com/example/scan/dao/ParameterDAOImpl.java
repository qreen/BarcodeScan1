package com.example.scan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.scan.entity.ParameterSetting;
import com.example.scan.sqlutil.DBOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParameterDAOImpl {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public ParameterDAOImpl(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context, "stock.db", null, 4);
    }

    public void insertParam(){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("insert into s_paramentSetting (numEnableInput,inexistEnableAdd,aginCheck,createDate,modifierDate) values(1,1,1,datetime('now','localtime'),datetime('now','localtime'))");
        db.close();  //关闭数据库操作
    }

    public void updateParam(ParameterSetting parameterSetting){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update s_paramentSetting set numEnableInput=?,inexistEnableAdd=?,aginCheck=?,modifierDate=datetime('now','localtime')" +
                        " where id=?",
                new Object[] { parameterSetting.getNumEnableInput(),parameterSetting.getInexistEnableAdd(),parameterSetting.getAginCheck(),parameterSetting.getId() });
        db.close();
    }

    public ParameterSetting ParameterSettingOne(){
        db = dbOpenHelper.getReadableDatabase();
        ParameterSetting parameterSetting = new ParameterSetting();
        // 用游标Cursor接收从数据库检索到的数据
        Cursor cursor = db.rawQuery("select * from s_paramentSetting",null);
        if (cursor.moveToFirst()) {// 依次取出数据
            parameterSetting.setId(cursor.getInt(cursor.getColumnIndex("id")));
            parameterSetting.setInexistEnableAdd(cursor.getInt(cursor.getColumnIndex("inexistEnableAdd")));
            parameterSetting.setAginCheck(cursor.getInt(cursor.getColumnIndex("aginCheck")));
            parameterSetting.setNumEnableInput(cursor.getInt(cursor.getColumnIndex("numEnableInput")));
            parameterSetting.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            parameterSetting.setModifierDate(cursor.getString(cursor.getColumnIndex("modifierDate")));
        }
        db.close();
        return parameterSetting;
    }
}
