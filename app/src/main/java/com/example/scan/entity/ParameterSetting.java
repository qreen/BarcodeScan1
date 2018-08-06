package com.example.scan.entity;

import java.util.Date;

public class ParameterSetting {
    //id
    private Integer id;
    //货物数量允许输入 1允许 2不允许
    private Integer numEnableInput;
    //货物条码不存在时，是否可以重新加入 1允许 2不允许
    private Integer inexistEnableAdd;
    //货物条码扫盘点过了，是否重复再盘一次 1允许 2不允许
    private Integer aginCheck;
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

    public Integer getNumEnableInput() {
        return numEnableInput;
    }

    public void setNumEnableInput(Integer numEnableInput) {
        this.numEnableInput = numEnableInput;
    }

    public Integer getInexistEnableAdd() {
        return inexistEnableAdd;
    }

    public void setInexistEnableAdd(Integer inexistEnableAdd) {
        this.inexistEnableAdd = inexistEnableAdd;
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

    public Integer getAginCheck() {
        return aginCheck;
    }

    public void setAginCheck(Integer aginCheck) {
        this.aginCheck = aginCheck;
    }

    public ParameterSetting() {

    }
}
