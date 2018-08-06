package com.example.scan.entity;

import java.util.Date;

public class ScanSublist {
    //id
    private Integer id;
    //编号
    private String serialNum;
    //货位
    private String goodsAllocation;
    //备注
    private String remark;
    //商品编码
    private String commodityCode;
    //数量
    private int commodityNum;

    //创建时间
    private String createDate;
    //编辑时间
    private String modifierDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getGoodsAllocation() {
        return goodsAllocation;
    }

    public void setGoodsAllocation(String goodsAllocation) {
        this.goodsAllocation = goodsAllocation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getModifierDate() {
        return modifierDate;
    }

    public void setModifierDate(String modifierDate) {
        this.modifierDate = modifierDate;
    }

    public ScanSublist() {
    }
}
