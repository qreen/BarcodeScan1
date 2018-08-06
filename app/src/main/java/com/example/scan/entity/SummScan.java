package com.example.scan.entity;

public class SummScan {
    //编号
    private String serialNum;
    //货位
    private String goodAllocation;
    //商品编号
    private String commodityCode;
    //计划数量
    private int planCommNum;
    //实际扫描数量
    private int actualCommNum;

    public String getCommodityCode() {
        return commodityCode;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getGoodAllocation() {
        return goodAllocation;
    }

    public void setGoodAllocation(String goodAllocation) {
        this.goodAllocation = goodAllocation;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public int getPlanCommNum() {
        return planCommNum;
    }

    public void setPlanCommNum(int planCommNum) {
        this.planCommNum = planCommNum;
    }

    public int getActualCommNum() {
        return actualCommNum;
    }

    public void setActualCommNum(int actualCommNum) {
        this.actualCommNum = actualCommNum;
    }

    public SummScan(){}
}
