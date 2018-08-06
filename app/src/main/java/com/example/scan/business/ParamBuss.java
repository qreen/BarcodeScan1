package com.example.scan.business;

import android.content.Context;

import com.example.scan.dao.ParameterDAOImpl;
import com.example.scan.entity.ParameterSetting;

public class ParamBuss {
    private Context context;
    private ParameterDAOImpl parameterDAO;

    public  ParamBuss(Context mcontext) {
        this.context = mcontext;
        parameterDAO = new ParameterDAOImpl(mcontext);
    }

    //修改参数
    public void editParameter(ParameterSetting parameterSetting){
        parameterDAO.updateParam(parameterSetting);
    }

    //查询参数
    public ParameterSetting paramOne(){
        return parameterDAO.ParameterSettingOne();

    }
}
