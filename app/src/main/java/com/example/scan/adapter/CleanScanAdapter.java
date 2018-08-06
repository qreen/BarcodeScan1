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

public class CleanScanAdapter extends BaseAdapter{

    List<ScanSublist> scanSublists = new ArrayList<>();
    Context context;

    public CleanScanAdapter(List<ScanSublist> scanSublists, Context context){
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
        View cleanScanItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clear_scan,null);
        TextView tvSerialNum = cleanScanItem.findViewById(R.id.tv_clean_serialNum);
        TextView tvGoodsAllocation = cleanScanItem.findViewById(R.id.tv_clean_goodsAllocation);
        TextView tvRemark = cleanScanItem.findViewById(R.id.tv_clean_remark);
        tvSerialNum.setText(scanSublists.get(position).getSerialNum());
        tvGoodsAllocation.setText(scanSublists.get(position).getGoodsAllocation());
        tvRemark.setText(scanSublists.get(position).getRemark());
        return cleanScanItem;
    }
}
