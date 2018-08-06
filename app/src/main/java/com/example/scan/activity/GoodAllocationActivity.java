package com.example.scan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scan.R;
import com.example.scan.adapter.GoodAllocationQueryAdapter;
import com.example.scan.business.ScanBuss;
import com.example.scan.entity.ScanSublist;

import java.util.ArrayList;
import java.util.List;

public class GoodAllocationActivity extends Activity {
    private EditText goodAllocation;
    private Button goodAllocationSearch;
    private TextView remark;
    private ListView goodAllocation_lv;
    private ScanBuss scanBuss;
    private List<ScanSublist> scanSublistList;
    private GoodAllocationQueryAdapter goodAllocationQueryAdapter;
    private long totalNum_values;
    private TextView totalNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_allocation);
        initVariable();
        initLister();
    }

    public void initVariable(){
        goodAllocation = findViewById(R.id.et_good_allocation);
        goodAllocationSearch = findViewById(R.id.bt_good_allocation_search);
        remark = findViewById(R.id.tv_remark);
        goodAllocation_lv = findViewById(R.id.lv_good_allocation_list);
        scanBuss = new ScanBuss(GoodAllocationActivity.this);
        totalNum = findViewById(R.id.tv_total_num);


        scanSublistList = new ArrayList<>();

        goodAllocationQueryAdapter = new GoodAllocationQueryAdapter(scanSublistList,GoodAllocationActivity.this);
        goodAllocation_lv.setAdapter(goodAllocationQueryAdapter);
    }

    public void initLister(){
        goodAllocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goodAllocation_val = goodAllocation.getText().toString().trim();
                scanSublistList = scanBuss.sublistList(null,goodAllocation_val);
                totalNum_values = 0;
                remark.setText("");
                if(scanSublistList.size()>0){
                    remark.setText(scanSublistList.get(0).getRemark());
                    totalNum_values = scanBuss.totalNum(scanSublistList.get(0).getSerialNum());
                }
                goodAllocationQueryAdapter.updateList(scanSublistList);

                totalNum.setText(totalNum_values + "");
            }
        });

        goodAllocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                goodAllocationSearch.requestFocus();
                return true;
            }
        });
    }
}
