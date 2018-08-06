package com.example.scan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scan.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseAdapter {

    List<HomeItem> homeItemList;
    Context context;

    public HomeAdapter(List<HomeItem> homeItems, Context context){
        homeItemList = homeItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return homeItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return homeItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View homeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,null);
        TextView tvName = homeView.findViewById(R.id.tv_home_item);
        ImageView ivIcon = homeView.findViewById(R.id.iv_home_item);
        tvName.setText(homeItemList.get(position).itemName);
        ivIcon.setImageResource(homeItemList.get(position).itemImagId);
        return homeView;
    }

}
