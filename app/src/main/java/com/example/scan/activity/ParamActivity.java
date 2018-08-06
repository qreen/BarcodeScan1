package com.example.scan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.scan.R;
import com.example.scan.business.ParamBuss;
import com.example.scan.entity.ParameterSetting;

public class ParamActivity extends Activity{
    private Button compBtn;
    private CheckBox aginCb;
    private CheckBox newAddCb;
    private CheckBox editNumCb;
    private ParamBuss paramBuss;
    private ParameterSetting parameterSetting;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramter);
        initVariable();
        initListening();
    }

    public void initVariable(){
        compBtn = findViewById(R.id.bt_complete);
        aginCb = findViewById(R.id.cb_agin);
        newAddCb = findViewById(R.id.cb_new_add);
        editNumCb = findViewById(R.id.cb_edit_num);
        paramBuss = new ParamBuss(ParamActivity.this);
        parameterSetting = paramBuss.paramOne();
        aginCb.setChecked(parameterSetting.getAginCheck()==1);
        newAddCb.setChecked(parameterSetting.getInexistEnableAdd()==1);
        editNumCb.setChecked(parameterSetting.getNumEnableInput()==1);
    }
    public void initListening(){
        compBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int agin_values = aginCb.isChecked()?1:2;
                int newAddCb_values = newAddCb.isChecked()?1:2;
                int editNumCb_values = editNumCb.isChecked()?1:2;
                parameterSetting.setAginCheck(agin_values);
                parameterSetting.setInexistEnableAdd(newAddCb_values);
                parameterSetting.setNumEnableInput(editNumCb_values);
                paramBuss.editParameter(parameterSetting);
                Intent intent = new Intent();
                intent.setClass(ParamActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
