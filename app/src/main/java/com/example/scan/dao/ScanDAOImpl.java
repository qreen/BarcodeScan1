package com.example.scan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.scan.entity.ScanSublist;
import com.example.scan.sqlutil.DBOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Min on 2016/12/15.
 */

public class ScanDAOImpl {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public ScanDAOImpl(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context, "stock.db", null, 4);
    }


    //扫描表添加
    public void sublistSave(ScanSublist scanSublist) {// 插入记录
        db = dbOpenHelper.getWritableDatabase();// 取得数据库操作
//        Date date =new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String sql = "insert into s_scanSublist (serialNum,goodsAllocation,remark,commodityCode,commodityNum,createDate,modifierDate) values(?,?,?,?,?,datetime('now','localtime'),datetime('now','localtime'))";
        db.execSQL(sql,
                new Object[] {scanSublist.getSerialNum(),scanSublist.getGoodsAllocation(), scanSublist.getRemark()
                        , scanSublist.getCommodityCode(),scanSublist.getCommodityNum()});
        db.close();  //关闭数据库操作
    }


    //扫描时修改数据
    public void updateSublist(ScanSublist scanSublist) {// 修改纪录
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update s_scanSublist set serialNum=?,goodsAllocation=?,remark=?,commodityNum=commodityNum+1," +
                        " modifierDate=datetime('now','localtime')  where" + " commodityCode=?",
                new Object[] { scanSublist.getSerialNum(),scanSublist.getGoodsAllocation(),scanSublist.getRemark(),scanSublist.getCommodityCode() });
        db.close();
    }


    //扫描时直接数据加1
    public void updateNum(String commodityCode) {// 修改纪录
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update s_scanSublist set commodityNum=commodityNum+1," +
                        " modifierDate=datetime('now','localtime')  where" + " commodityCode=?",
                new Object[] { commodityCode });
        db.close();
    }

    //扫描表修改数量
    public void updateSublistNum(ScanSublist scanSublist) {// 修改纪录
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("update s_scanSublist set commodityNum=?, modifierDate=datetime('now','localtime')  where" + " id=?",
                new Object[] { scanSublist.getCommodityNum(),scanSublist.getId() });
        db.close();
    }

    // 删除多编号下的纪录
    public void delete(String[] serialNums) {
        if(serialNums.length>0){
            db = dbOpenHelper.getWritableDatabase();
            for (String serialNum : serialNums){
                db.execSQL("delete from s_scanSublist where serialNum=?", new Object[] { serialNum });
            }
            db.close();
        }
    }

    // 删除一类编号下的纪录
    public void deleteOne(String serialNum) {
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from s_scanSublist where serialNum=?", new Object[] { serialNum });
        db.close();
    }

    //根据id查询子表
    public ScanSublist findSublistById(String id) {// 根据ID查找纪录
        ScanSublist scanSublist = null;
        db = dbOpenHelper.getReadableDatabase();
        // 用游标Cursor接收从数据库检索到的数据
        Cursor cursor = db.rawQuery("select * from s_scanSublist where id=?", new String[] { id.toString() });
        if (cursor.moveToFirst()) {// 依次取出数据
            scanSublist = new ScanSublist();
            scanSublist.setId(cursor.getInt(cursor.getColumnIndex("id")));
            scanSublist.setSerialNum(cursor.getString(cursor.getColumnIndex("serialNum")));
            scanSublist.setGoodsAllocation(cursor.getString(cursor.getColumnIndex("goodsAllocation")));
            scanSublist.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            scanSublist.setCommodityCode(cursor.getString(cursor.getColumnIndex("commodityCode")));
            scanSublist.setCommodityNum(cursor.getInt(cursor.getColumnIndex("commodityNum")));
        }
        db.close();
        return scanSublist;
    }

    //查询是否商品条码编号是否存在，1：没查到   2：查到）
    public int checkCommodityCode(String serialNum, String commodityCode){
        int status = 1;
        db = dbOpenHelper.getReadableDatabase();
        String sql = "select * from s_scanSublist where commodityCode = '"+commodityCode+"' and serialNum = '"+serialNum+"'";
        Cursor cursor =  db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            status = 2;
        }
        return status;
    }

    //查询去重后的主数据信息
    public List<ScanSublist> findDistinctData() {
        List<ScanSublist> scanSublistList = new ArrayList<>();
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT serialNum,goodsAllocation,remark from s_scanSublist where serialNum notnull", null);
        while (cursor.moveToNext()) {
            ScanSublist scanSublist = new ScanSublist();
            scanSublist.setGoodsAllocation(cursor.getString(cursor.getColumnIndex("goodsAllocation")));
            scanSublist.setSerialNum(cursor.getString(cursor.getColumnIndex("serialNum")));
            scanSublist.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            scanSublistList.add(scanSublist);
        }
        db.close();
        return scanSublistList;
    }

    //扫描表查询全部
    public List<ScanSublist> findMainAll(String serialNum,String goodsAllocation) {
        List<ScanSublist> lists = new ArrayList<>();
        db = dbOpenHelper.getReadableDatabase();
        // Cursor cursor=db.rawQuery("select * from t_users limit ?,?", new
        // String[]{offset.toString(),maxLength.toString()});
        // //这里支持类型MYSQL的limit分页操作

        StringBuffer sql = new StringBuffer();
        sql.append(" select * from s_scanSublist where commodityNum != 0  ");
        //0代表基础查询(全查),1代表编号查询，3代表货位查询
        if(null != serialNum && "" != serialNum){
            sql.append(" and serialNum = '"+serialNum+"'");
        }
        if(null != goodsAllocation && "" != goodsAllocation){
            sql.append(" and goodsAllocation = '"+goodsAllocation+"'");
        }
        sql.append(" order by modifierDate desc");
        Cursor cursor = db.rawQuery(sql.toString(), null);
        while (cursor.moveToNext()) {
            ScanSublist scanSublist = new ScanSublist();
            scanSublist.setId(cursor.getInt(cursor.getColumnIndex("id")));
            scanSublist.setSerialNum(cursor.getString(cursor.getColumnIndex("serialNum")));
            scanSublist.setGoodsAllocation(cursor.getString(cursor.getColumnIndex("goodsAllocation")));
            scanSublist.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            scanSublist.setCommodityNum(cursor.getInt(cursor.getColumnIndex("commodityNum")));
            scanSublist.setCommodityCode(cursor.getString(cursor.getColumnIndex("commodityCode")));
            scanSublist.setCreateDate(cursor.getString(cursor.getColumnIndex("createDate")));
            scanSublist.setModifierDate(cursor.getString(cursor.getColumnIndex("modifierDate")));
            lists.add(scanSublist);
        }
        db.close();
        return lists;
    }

    //统计所有记录数
    public long getCount() {
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from s_scanSublist ", null);
        cursor.moveToFirst();
        db.close();
        return cursor.getLong(0);
    }

    //查询主表编号是否存在
    public ScanSublist existNum(String serialNum){
        db = dbOpenHelper.getReadableDatabase();
        ScanSublist scanSublist = null;
        Cursor cursor = db.rawQuery("select * from s_scanSublist where serialNum=?", new String[] { serialNum });
        if(cursor.moveToFirst()){
            scanSublist = new ScanSublist();
            scanSublist.setId(cursor.getInt(cursor.getColumnIndex("id")));
            scanSublist.setSerialNum(cursor.getString(cursor.getColumnIndex("serialNum")));
            scanSublist.setGoodsAllocation(cursor.getString(cursor.getColumnIndex("goodsAllocation")));
            scanSublist.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
        }
        db.close();
        return scanSublist;
    }

    //删除所有数据
    public void removeAll(){
        db = dbOpenHelper.getWritableDatabase();
        db.execSQL("delete from s_scanSublist ");
        db.close();
    }

    //计算扫描总数
    public long totalNum(String serialNum){
        long totalNum = 0;
        db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(commodityNum) as totalCommNum from s_scanSublist where serialNum=?", new String[] { serialNum });
        if(cursor.moveToFirst()){
            totalNum = cursor.getLong(cursor.getColumnIndex("totalCommNum"));
        }
        db.close();
        return totalNum;
    }
}
