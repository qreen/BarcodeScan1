package com.example.scan.entity;

public class SummaryData {
    private String serialNum;
    private String goodAllocation;
    private String commodityCode;
    private int predictNum;
    private int actualNum;

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

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public int getPredictNum() {
        return predictNum;
    }

    public void setPredictNum(int predictNum) {
        this.predictNum = predictNum;
    }

    public int getActualNum() {
        return actualNum;
    }

    public void setActualNum(int actualNum) {
        this.actualNum = actualNum;
    }
}
