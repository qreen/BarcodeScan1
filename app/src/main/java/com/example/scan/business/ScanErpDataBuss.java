package com.example.scan.business;

import android.content.Context;

import com.example.scan.dao.ScanDAOImpl;
import com.example.scan.dao.ScanErpDataDAOImpl;
import com.example.scan.entity.ScanErpData;
import com.example.scan.entity.ScanErpDataImport;
import com.example.scan.entity.ScanSublist;
import com.example.scan.entity.SummScan;

import java.util.ArrayList;
import java.util.List;

public class ScanErpDataBuss {
    private ScanErpDataDAOImpl scanErpDataDAO;
    private ScanDAOImpl scanDAO;
    private Context context;

    public  ScanErpDataBuss(Context mcontext) {
        this.context = mcontext;
        scanErpDataDAO = new ScanErpDataDAOImpl(mcontext);
        scanDAO = new  ScanDAOImpl(mcontext);
    }

    //批量添加
    public void insertScanErpData(List<ScanErpDataImport> scanErpDataImportList){
        scanErpDataDAO.insertScanErpData(scanErpDataImportList);
    }

    //批量删除
    public void deleteScanErpData(String serialNum){
        scanErpDataDAO.deleteScanErpData(serialNum);
    }

    //汇总数据
    public List<SummScan> sumScanData(String serialNum){
        String goodAllocation = "";
        List<SummScan> summScanList = new ArrayList<>();
        List<ScanErpData> scanErpDataList = scanErpDataDAO.listBySerialNum(serialNum);
        List<ScanSublist> scanSublistList = scanDAO.findMainAll(serialNum,null);

        if(scanSublistList.size()>0){
            goodAllocation = scanSublistList.get(0).getGoodsAllocation();
        }

        for (ScanErpData scanErpData : scanErpDataList){
            SummScan summScan = new SummScan();
            summScan.setCommodityCode(scanErpData.getCommodityCode());
            summScan.setPlanCommNum(scanErpData.getCommodityNum());
            summScan.setSerialNum(scanErpData.getSerialNum());
            summScan.setActualCommNum(0);
            summScan.setGoodAllocation(goodAllocation);
            summScanList.add(summScan);
        }
        for (ScanSublist scanSublist : scanSublistList){
            Boolean fag = false;
            for (SummScan summScan : summScanList){
                if(scanSublist.getCommodityCode().equals(summScan.getCommodityCode())){
                    summScan.setActualCommNum(scanSublist.getCommodityNum());
                    fag = true;
                }
            }
            if(!fag){
                SummScan summScan = new SummScan();
                summScan.setSerialNum(scanSublist.getSerialNum());
                summScan.setCommodityCode(scanSublist.getCommodityCode());
                summScan.setActualCommNum(scanSublist.getCommodityNum());
                summScan.setGoodAllocation(goodAllocation);
                summScan.setPlanCommNum(0);
                summScanList.add(summScan);
            }
        }
        return summScanList;
    }

    //查询所以的serialNum
    public List<String> serialNumList(){
        return scanErpDataDAO.findAllSerialNum();
    }

    //查询根据serialNum
    public List<ScanErpData> findBySerialNum(String serialNum){
        return scanErpDataDAO.listBySerialNum(serialNum);
    }
}
