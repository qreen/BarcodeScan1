package com.example.scan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.scan.R;
import com.example.scan.adapter.CleanScanAdapter;
import com.example.scan.business.ScanBuss;
import com.example.scan.entity.ScanSublist;

import java.util.List;

public class QueryActivity extends Activity {
    private List<ScanSublist> scanSublistList;
    private ListView listView;
    private CleanScanAdapter cleanScanAdapter;
    private ScanBuss scanBuss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initVariable();
        listView.setAdapter(cleanScanAdapter);
        initListening();
    }

    public void initVariable(){
        scanBuss = new ScanBuss(QueryActivity.this);
        listView = findViewById(R.id.lv_query_scan_list);
        scanSublistList = scanBuss.scanMainList();
        cleanScanAdapter = new CleanScanAdapter(scanSublistList,QueryActivity.this);
    }

    public void initListening(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long id) {
                ScanSublist scanSublist = (ScanSublist) adapterView.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.setClass(QueryActivity.this,SummActivity.class);
                intent.putExtra("serialNum",scanSublist.getSerialNum());
                startActivity(intent);
            }
        });
    }

}
