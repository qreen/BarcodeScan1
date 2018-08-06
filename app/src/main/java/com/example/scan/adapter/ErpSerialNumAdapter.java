package com.example.scan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scan.R;
import com.example.scan.entity.ScanErpData;

import java.util.ArrayList;
import java.util.List;

public class ErpSerialNumAdapter extends BaseAdapter {
    List<ScanErpData> scanErpDataList = new ArrayList<>();
    Context context;

    public ErpSerialNumAdapter(List<ScanErpData> scanErpDataList, Context context) {
        this.scanErpDataList = scanErpDataList;
        this.context = context;
    }


    public void updateList(List<ScanErpData> scanErpDataList) {
        this.scanErpDataList = scanErpDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scanErpDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return scanErpDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View scanItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_erp_data_serial_query, null);
        TextView tvCode = scanItem.findViewById(R.id.tv_erp_data_commodity_code);
        TextView tvNum = scanItem.findViewById(R.id.tv_erp_data_commodity_num);
        tvCode.setText(scanErpDataList.get(position).getCommodityCode());
        tvNum.setText(scanErpDataList.get(position).getCommodityNum() + "");
        return scanItem;
    }
}
