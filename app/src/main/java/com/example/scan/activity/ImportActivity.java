package com.example.scan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scan.R;
import com.example.scan.adapter.ImportAdapter;
import com.example.scan.business.ScanErpDataBuss;
import com.example.scan.entity.ScanErpData;
import com.example.scan.entity.ScanErpDataImport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImportActivity extends Activity {
    private List<String> serNumList;
    private ImportAdapter importAdapter;
    private ListView listView;
    private ScanErpDataBuss scanErpDataBuss;
    private List<String> erpDataSerialNums = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        initVariable();
        initClick();
        listView.setAdapter(importAdapter);
    }

    public void initVariable() {
        scanErpDataBuss = new ScanErpDataBuss(ImportActivity.this);
        erpDataSerialNums = scanErpDataBuss.serialNumList();
        serNumList = new ArrayList<>();
        listView = findViewById(R.id.lv_import_list);
        importAdapter = new ImportAdapter(serNumList, ImportActivity.this);
        get();


    }

    private void initClick() {
        importAdapter.setClickBtnImport(new ImportAdapter.ClickBtnImport() {

            @Override
            public void onBtnClick(final String serialNum) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://qtxcx.shinkeer.com/scanData/findBySerialNum?serialNum=" + serialNum)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ImportActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ImportActivity.this, "获取数据失败了", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code() == 200) {
                            String data_str = response.body().string();
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<ScanErpDataImport>>() {
                            }.getType();
                            final List<ScanErpDataImport> list = gson.fromJson(data_str, listType);
                            ImportActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(ImportActivity.this)
                                            .setTitle("提示")
                                            .setMessage("确定要导入编号为：" + serialNum + "的商品数据吗？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    scanErpDataBuss.insertScanErpData(list);
                                                    serNumList.remove(serialNum);

                                                    importAdapter.updateList(serNumList);

                                                    Toast.makeText(ImportActivity.this, "导入成功", Toast.LENGTH_LONG).show();

                                                }
                                            }).setNegativeButton("取消", null).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void get() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://qtxcx.shinkeer.com/scanData/findMainData")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ImportActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "获取数据失败了", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String data_str = response.body().string();
                    Gson gson = new Gson();

                    Type listType = new TypeToken<List<ScanErpData>>() {
                    }.getType();
                    List<ScanErpData> list = gson.fromJson(data_str, listType);
                    for (ScanErpData scanErpData : list) {
                        boolean flag = false;
                        for (String serialNum : erpDataSerialNums) {
                            if (scanErpData.getSerialNum().equals(serialNum)) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            serNumList.add(scanErpData.getSerialNum());
                        }
                    }
                    ImportActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            importAdapter.updateList(serNumList);
                        }
                    });

                }

            }
        });
    }

}
