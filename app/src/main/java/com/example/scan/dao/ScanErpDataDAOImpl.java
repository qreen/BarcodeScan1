package com.example.scan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.scan.entity.ScanErpData;
import com.example.scan.entity.ScanErpDataImport;
import com.example.scan.entity.ScanSublist;
import com.example.scan.sqlutil.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ScanErpDataDAOImpl {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public ScanErpDataDAOImpl(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context, "stock.db", null, 4);

    }
    //批量插入
    public void insertScanErpData(List<ScanErpDataImport> scanErpDataImportList){
        db = dbOpenHelper.getWritableDatabase();
        for (ScanErpDataImport scanErpDataImport : scanErpDataImportList){
            db.execSQL("insert into s_scanErpData (serialNum,commodityCode,commodityNum,createDate) values(?,?,?,datetime('now','localtime'))",
                    new Object[]{scanErpDataImport.getSerialNum(),scanErpDataImport.getCommodityCode(),scanErpDataImport.getCommodityNum()});
        }
        db.close();
    }

    //批量删除
    public void deleteScanErpData(String serialNum){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from s_scanErpData where serialNum=?", new Object[] { serialNum });
        db.close();
    }

    //根据编号的list
    public List<ScanErpData> listBySerialNum(String serialNum){
        db = dbOpenHelper.getReadableDatabase();
        List<ScanErpData> scanErpDataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from s_scanErpData where serialNum = ?", new String[]{serialNum});
        while (cursor.moveToNext()) {
            ScanErpData scanErpData = new ScanErpData();
            scanErpData.setCommodityNum(cursor.getInt(cursor.getColumnIndex("commodityNum")));
            scanErpData.setSerialNum(cursor.getString(cursor.getColumnIndex("serialNum")));
            scanErpData.setCommodityCode(cursor.getString(cursor.getColumnIndex("commodityCode")));
            scanErpData.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            scanErpDataList.add(scanErpData);
        }
        db.close();
        return scanErpDataList;
    }
    //查询该编号下的该条码是否存在
    public boolean checkCodeExist(String serialNum,String commodityCode){
        boolean flag = false;
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from s_scanErpData where serialNum = ? and commodityCode = ?", new String[]{serialNum,commodityCode});
        if(cursor.moveToNext()){
            flag = true;
        }
        db.close();
        return flag;
    }

    //查询该编号是否存在
    public boolean checkSerialNumExist(String serialNum){
        boolean flag = false;
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from s_scanErpData where serialNum = ?", new String[]{serialNum});
        if(cursor.moveToNext()){
            flag = true;
        }
        db.close();
        return flag;
    }
    //查询所以的serialNum
    public List<String> findAllSerialNum(){
        List<String> serialNums = new ArrayList<>();
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT serialNum from s_scanErpData", null);
        while (cursor.moveToNext()) {
            serialNums.add(cursor.getString(cursor.getColumnIndex("serialNum")));
        }
        db.close();
        return serialNums;
    }
}
