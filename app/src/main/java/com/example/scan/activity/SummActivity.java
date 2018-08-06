package com.example.scan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scan.R;
import com.example.scan.adapter.SummAdapter;
import com.example.scan.business.ScanBuss;
import com.example.scan.business.ScanErpDataBuss;
import com.example.scan.entity.SummScan;
import com.example.scan.entity.SummaryData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SummActivity extends Activity {
    private List<SummScan> summScanList;
    private SummAdapter summAdapter;
    private ScanErpDataBuss scanErpDataBuss;
    private ListView summView;
    private Button btComplete;
    List<SummaryData> summaryDataList;
    private ScanBuss scanBuss;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_summ);
        initVariable();
        summView.setAdapter(summAdapter);
        initLister();
    }

    public void initVariable() {
        scanErpDataBuss = new ScanErpDataBuss(SummActivity.this);
        Intent intent = getIntent();
        String serialNum = intent.getStringExtra("serialNum");
        summScanList = scanErpDataBuss.sumScanData(serialNum);
        scanBuss = new ScanBuss(SummActivity.this);
        summAdapter = new SummAdapter(summScanList, SummActivity.this);
        summView = findViewById(R.id.lv_summary_data);
        btComplete = findViewById(R.id.bt_complete);
    }

    public void initLister() {
        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SummActivity.this)
                        .setTitle("提醒")
                        .setMessage("确定要提交这批数据吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OkHttpClient client = new OkHttpClient();
                                initSummDataList();
                                Gson gs = new Gson();
                                //创建Form表单对象，可以add多个键值队
                                FormBody formBody = new FormBody.Builder()
                                        .add("summaryDataList", gs.toJson(summaryDataList))
                                        .build();
                                //创建一个Request
                                Request request = new Request.Builder()
                                        .url("https://qtxcx.shinkeer.com/summaryData/insertData")
                                        .post(formBody)
                                        .build();
                                //发起异步请求，并加入回调
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        SummActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(SummActivity.this, "获取数据失败了", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        if (response.code() == 200) {
                                            String data_str = response.body().string();
                                            Gson gson = new Gson();
                                            final Map map = gson.fromJson(data_str, Map.class);
                                            SummActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    String data = map.get("msg").toString();
                                                    if (data.equals("success")) {
                                                        scanErpDataBuss.deleteScanErpData(summScanList.get(0).getSerialNum());
                                                        scanBuss.deleteOne(summScanList.get(0).getSerialNum());
                                                        Toast.makeText(SummActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent();
                                                        intent.setClass(SummActivity.this,QueryActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                        }

                                    }
                                });
                            }
                        }).setNegativeButton("取消",null).show();

            }
        });
    }

    public void initSummDataList() {
        summaryDataList = new ArrayList<>();
        for (SummScan summScan : summScanList) {
            SummaryData summaryData = new SummaryData();
            summaryData.setSerialNum(summScan.getSerialNum());
            summaryData.setGoodAllocation(summScan.getGoodAllocation());
            summaryData.setCommodityCode(summScan.getCommodityCode());
            summaryData.setPredictNum(summScan.getPlanCommNum());
            summaryData.setActualNum(summScan.getActualCommNum());
            summaryDataList.add(summaryData);
        }
    }
}
