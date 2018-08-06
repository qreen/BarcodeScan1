package com.example.scan.entity;

public class ScanErpDataImport {
    //id
    private String id;
    //编号
    private String serialNum;
    //商品编码
    private String commodityCode;
    //数量
    private int commodityNum;

    //创建时间
    private String createDate;
    public ScanErpDataImport() {
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public int getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(int commodityNum) {
        this.commodityNum = commodityNum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
