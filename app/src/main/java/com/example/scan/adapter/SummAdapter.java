package com.example.scan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.scan.R;
import com.example.scan.entity.ScanSublist;
import com.example.scan.entity.SummScan;

import java.util.ArrayList;
import java.util.List;

public class SummAdapter extends BaseAdapter {
    List<SummScan> summScanList = new ArrayList<>();
    Context context;

    public SummAdapter(List<SummScan> summScans, Context context){
        this.summScanList = summScans;
        this.context = context;
    }


    public void updateList(List<SummScan> summScanList){
        this.summScanList = summScanList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return summScanList.size();
    }

    @Override
    public Object getItem(int position) {
        return summScanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View summaryScanItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary,null);
        TextView tvSummCode = summaryScanItem.findViewById(R.id.tv_summary_comm_code);
        TextView tvSummPlanNum = summaryScanItem.findViewById(R.id.tv_planCommNum);
        TextView tvSummAuNum = summaryScanItem.findViewById(R.id.tv_actualCommNum);
        tvSummCode.setText(summScanList.get(position).getCommodityCode());
        tvSummPlanNum.setText(summScanList.get(position).getPlanCommNum()+"");
        tvSummAuNum.setText(summScanList.get(position).getActualCommNum()+"");
        return summaryScanItem;
    }

}
