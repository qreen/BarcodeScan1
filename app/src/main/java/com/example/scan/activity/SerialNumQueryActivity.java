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
import android.widget.Toast;

import com.example.scan.R;
import com.example.scan.adapter.SerialNumQueryAdapter;
import com.example.scan.business.ScanBuss;
import com.example.scan.entity.ScanSublist;

import java.util.ArrayList;
import java.util.List;

public class SerialNumQueryActivity extends Activity {

    private EditText serialNum;
    private Button serialSearch;
    private TextView remark;
    private ListView serialNum_lv;
    private ScanBuss scanBuss;
    private List<ScanSublist> scanSublistList;
    private SerialNumQueryAdapter serialNumQueryAdapter;
    private long totalNum_values;
    private TextView totalNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_num_query);
        initVariable();
        initLister();
    }

    public void initVariable(){
        serialNum = findViewById(R.id.et_serialNum);
        serialSearch = findViewById(R.id.bt_serial_search);
        remark = findViewById(R.id.tv_remark);
        serialNum_lv = findViewById(R.id.lv_serial_num_list);
        scanBuss = new ScanBuss(SerialNumQueryActivity.this);
        totalNum = findViewById(R.id.tv_total_num);

        scanSublistList = new ArrayList<>();

        serialNumQueryAdapter = new SerialNumQueryAdapter(scanSublistList,SerialNumQueryActivity.this);
        serialNum_lv.setAdapter(serialNumQueryAdapter);
    }

    public void initLister(){
        serialSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serialNum_val = serialNum.getText().toString().trim();
                if (serialNum_val==null || serialNum_val.equals("")){
                    Toast.makeText(SerialNumQueryActivity.this, "编号不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                scanSublistList = scanBuss.sublistList(serialNum_val,null);
                remark.setText("");
                totalNum_values = 0;
                if(scanSublistList.size()>0){
                    remark.setText(scanSublistList.get(0).getRemark());
                }
                serialNumQueryAdapter.updateList(scanSublistList);

                totalNum_values = scanBuss.totalNum(serialNum_val);
                totalNum.setText(totalNum_values + "");
            }
        });

        serialNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                serialSearch.requestFocus();
                return true;
            }
        });
    }
}
