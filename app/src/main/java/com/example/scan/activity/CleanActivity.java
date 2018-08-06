package com.example.scan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class CleanActivity extends Activity{
    private List<ScanSublist> scanSublistList;
    private ListView listView;
    private CleanScanAdapter cleanScanAdapter;
    private ScanBuss scanBuss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        initVariable();
        listView.setAdapter(cleanScanAdapter);
        initListening();
    }

    public void initVariable(){
        scanBuss = new ScanBuss(CleanActivity.this);
        listView = findViewById(R.id.lv_clean_scan_list);
        scanSublistList = scanBuss.scanMainList();
        cleanScanAdapter = new CleanScanAdapter(scanSublistList,CleanActivity.this);
    }

    public void initListening(){
        listView.setOnItemClickListener(new CleanListItemClickListener());
    }

    private class CleanListItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView adapterView, View view, int position, long id) {
            final ScanSublist scanSublist = (ScanSublist) adapterView.getAdapter().getItem(position);
            new AlertDialog.Builder(CleanActivity.this)
                    .setTitle("提示")
                    .setMessage("确定要删除选中的数据吗")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            scanBuss.deleteOne(scanSublist.getSerialNum());
                            scanSublistList = scanBuss.scanMainList();
                            cleanScanAdapter.updateList(scanSublistList);
                        }
                    })
                    .setNegativeButton("取消",null)
                    .show();

        }
    }
}
