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
import com.example.scan.adapter.ErpSerialNumAdapter;
import com.example.scan.adapter.ImportAdapter;
import com.example.scan.business.ScanErpDataBuss;
import com.example.scan.entity.ScanErpData;
import com.example.scan.entity.ScanSublist;

import java.util.ArrayList;
import java.util.List;

public class ImportQueryActivity extends Activity {

    private EditText serialNum;
    private Button serialSearch;
    private ListView lvScan;
    private ScanErpDataBuss scanErpDataBuss;
    private List<ScanErpData> scanErpDataList;
    private ErpSerialNumAdapter erpSerialNumAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_query);
        initVariable();
        initLister();
    }

    public void initVariable() {
        serialNum = findViewById(R.id.et_erp_data_serialNum);
        serialSearch = findViewById(R.id.bt_erp_data_serial_search);
        lvScan = findViewById(R.id.lv_erp_data_serial_num_list);
        scanErpDataBuss = new ScanErpDataBuss(ImportQueryActivity.this);

        scanErpDataList = new ArrayList<>();

        erpSerialNumAdapter = new ErpSerialNumAdapter(scanErpDataList, ImportQueryActivity.this);
        lvScan.setAdapter(erpSerialNumAdapter);
    }

    public void initLister() {
        serialSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serialNum_val = serialNum.getText().toString().trim();
                if (serialNum_val==null || serialNum_val.equals("")){
                    Toast.makeText(ImportQueryActivity.this, "编号不能为空", Toast.LENGTH_LONG).show();
                }
                scanErpDataList = scanErpDataBuss.findBySerialNum(serialNum_val);

                erpSerialNumAdapter.updateList(scanErpDataList);
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
