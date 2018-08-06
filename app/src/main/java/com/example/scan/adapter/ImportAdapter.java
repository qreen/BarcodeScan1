package com.example.scan.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scan.R;
import com.example.scan.activity.ImportActivity;
import com.example.scan.business.ScanErpDataBuss;
import com.example.scan.entity.ScanErpData;
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

public class ImportAdapter extends BaseAdapter {
    List<String> serialNumList = new ArrayList<>();
    Context context;

    public ImportAdapter(List<String> serialNums, Context context) {
        this.serialNumList = serialNums;
        this.context = context;

    }


    public void updateList(List<String> serialNums) {
        this.serialNumList = serialNums;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return serialNumList.size();
    }

    @Override
    public Object getItem(int position) {
        return serialNumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View serialNumItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_import, null);
        TextView importSerNum = serialNumItem.findViewById(R.id.tv_import_serNum);
        Button btn = serialNumItem.findViewById(R.id.bt_import);
        final String serialNum=serialNumList.get(position);
        importSerNum.setText(serialNum);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnImport.onBtnClick(serialNum);
            }
        });
        return serialNumItem;
    }

    private ClickBtnImport clickBtnImport;

    public void setClickBtnImport(ClickBtnImport clickBtnImport) {
        this.clickBtnImport = clickBtnImport;
    }

    public interface ClickBtnImport {
        public void onBtnClick(String serialNum);
    }
}
