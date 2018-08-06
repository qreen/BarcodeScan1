package com.example.scan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.scan.R;
import com.example.scan.adapter.HomeAdapter;
import com.example.scan.adapter.HomeItem;
import com.example.scan.entity.ParameterSetting;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity{
    List<HomeItem> homeItemList;
    GridView homeView;
    HomeAdapter homeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fillData();
        initVariable();
        homeAdapter = new HomeAdapter(homeItemList,this);
        homeView.setAdapter(homeAdapter);
        initListening();
    }

    //初始化变量
    public void initVariable(){
        homeView = findViewById(R.id.gv_home);
    }

    //初始化监听器
    public void initListening(){
        homeView.setOnItemClickListener(new GridItemClickListener());
    }


    //填充数据
    public void fillData(){
        homeItemList = new ArrayList<>();
        homeItemList.add(new HomeItem(getString(R.string.commodity_scanning_manage), R.drawable.scan));
        homeItemList.add(new HomeItem(getString(R.string.data_query_manage), R.drawable.search));
        homeItemList.add(new HomeItem(getString(R.string.parameter_setting_manage), R.drawable.setting));
        homeItemList.add(new HomeItem(getString(R.string.data_clean_manage), R.drawable.clean));
        homeItemList.add(new HomeItem(getString(R.string.comm_import), R.drawable.import_img));
    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView adapterView, View view, int position, long id) {
            HomeItem homeItem = (HomeItem) adapterView.getAdapter().getItem(position);
            String _menuName = homeItem.itemName;
            if(_menuName.equals(getString(R.string.commodity_scanning_manage))){
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this,ScanActivity.class);
                startActivity(intent);
                return;
            }
            if(_menuName.equals(getString(R.string.data_query_manage))){
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this,QueryWayActivity.class);
                startActivity(intent);
                return;
            }
            if(_menuName.equals(getString(R.string.parameter_setting_manage))){
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this,ParamActivity.class);
                startActivity(intent);
                return;
            }
            if(_menuName.equals(getString(R.string.data_clean_manage))){
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this,CleanActivity.class);
                startActivity(intent);
                return;

            }
            if(_menuName.equals(getString(R.string.comm_import))){
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this,ImportActivity.class);
                startActivity(intent);
                return;
            }
        }
    }
}
