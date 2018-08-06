package com.example.scan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scan.R;
import com.example.scan.entity.ScanSublist;

import java.util.ArrayList;
import java.util.List;

public class GoodAllocationQueryAdapter extends BaseAdapter {

    List<ScanSublist> scanSublists = new ArrayList<>();
    Context context;

    public GoodAllocationQueryAdapter(List<ScanSublist> scanSublists, Context context){
        this.scanSublists = scanSublists;
        this.context = context;
    }
    public void updateList(List<ScanSublist> scanSublistList){
        this.scanSublists = scanSublistList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scanSublists.size();
    }

    @Override
    public Object getItem(int position) {
        return scanSublists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View scanItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_good_allocation_query,null);
        TextView serialNum = scanItem.findViewById(R.id.tv_serial_num);
        TextView commCode = scanItem.findViewById(R.id.tv_commodity_code);
        TextView commNum = scanItem.findViewById(R.id.tv_commodity_num);
        serialNum.setText(scanSublists.get(position).getSerialNum());
        commCode.setText(scanSublists.get(position).getCommodityCode());
        commNum.setText(scanSublists.get(position).getCommodityNum()+"");
        return scanItem;
    }
}
