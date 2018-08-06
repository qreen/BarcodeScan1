package com.example.scan.business;

import android.content.Context;

import com.example.scan.dao.ParameterDAOImpl;
import com.example.scan.dao.ScanDAOImpl;
import com.example.scan.dao.ScanErpDataDAOImpl;
import com.example.scan.entity.ParameterSetting;
import com.example.scan.entity.ScanSublist;

import java.util.List;

public class ScanBuss {
    private ScanDAOImpl scanDAO;
    private ParameterDAOImpl parameterDAO;
    private ScanErpDataDAOImpl scanErpDataDAO;
    private Context context;

    public ScanBuss(Context mcontext) {
        this.context = mcontext;
        scanDAO = new ScanDAOImpl(mcontext);
        parameterDAO = new ParameterDAOImpl(mcontext);
        scanErpDataDAO = new ScanErpDataDAOImpl(mcontext);
    }


    //查询设置表中的数据
    public ParameterSetting paramOne() {
        return parameterDAO.ParameterSettingOne();
    }

    //方式一扫描表插入
    public void insertSublist(ScanSublist scanSublist) {
        int status = scanDAO.checkCommodityCode(scanSublist.getSerialNum(), scanSublist.getCommodityCode());
        if (status == 1) {
            scanDAO.sublistSave(scanSublist);
        } else {
            scanDAO.updateNum(scanSublist.getCommodityCode());
        }
    }

    //匹配扫描的条码在ERP表中是否存在该条码
    public boolean checkCodeExist(String serialNum,String commodityCode) {
        boolean flag = true;
        int status = scanDAO.checkCommodityCode(serialNum, commodityCode);
        if (status==1){
            flag = scanErpDataDAO.checkCodeExist(serialNum, commodityCode);
        }
         return flag;
    }

    //扫描表扫描时展示的数据
    public List<ScanSublist> sublistList(String serialNum, String goodsAllocation) {
        List<ScanSublist> scanSublistList = scanDAO.findMainAll(serialNum, goodsAllocation);
        return scanSublistList;
    }

    //修改商品数量
    public void updateCommNum(ScanSublist scanSublist) {
        scanDAO.updateSublistNum(scanSublist);
    }

    //删除一类编号的数据
    public void deleteOne(String serialNum) {
        scanDAO.deleteOne(serialNum);
    }

    //删除多类编号的数据
    public void deleteSublist(String[] serialNums) {
        scanDAO.delete(serialNums);
    }

    //删除整张表数据
    public void remove() {
        scanDAO.removeAll();
    }

    //查询去重后的主数据
    public List<ScanSublist> scanMainList() {
        List<ScanSublist> scanSublistList = scanDAO.findDistinctData();
        return scanSublistList;
    }

    //查询所有的数量
    public long totalNum(String serialNum) {
        long totalNum = scanDAO.totalNum(serialNum);
        return totalNum;
    }

    //查询编号是否存在 返回该对象
    public ScanSublist checkSerNumExist(String serialNum) {
        return scanDAO.existNum(serialNum);
    }

    //查询erpData表中是否有serialNum编号
    public boolean checkErpSerialNumExist(String serialNum) {
        return scanErpDataDAO.checkSerialNumExist(serialNum);
    }


}
