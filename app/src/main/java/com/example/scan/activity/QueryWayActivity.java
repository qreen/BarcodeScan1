package com.example.scan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.scan.R;
import com.example.scan.business.ScanBuss;

public class QueryWayActivity extends Activity{
    private RadioButton serialNum_query;
    private RadioButton good_allocation_query;
    private RadioButton import_query;
    private RadioButton summary_query;
    private ScanBuss scanBuss;
    private Button confirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_way);
        initVariable();
        initLister();
    }

    public void initVariable(){
        serialNum_query = findViewById(R.id.rb_serial_num);
        good_allocation_query = findViewById(R.id.rb_good_allocation);
        import_query = findViewById(R.id.rb_import);
        summary_query = findViewById(R.id.rb_summary);
        confirm = findViewById(R.id.bt_confirm);
        scanBuss = new ScanBuss(QueryWayActivity.this);
    }

    public void initLister(){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean serialNum_query_val =  serialNum_query.isChecked();
                boolean good_allocation_query_val =  good_allocation_query.isChecked();
                boolean import_query_val =  import_query.isChecked();
                boolean summary_query_val =  summary_query.isChecked();

                if(serialNum_query_val){
                    Intent intent = new Intent();
                    intent.setClass(QueryWayActivity.this,SerialNumQueryActivity.class);
                    startActivity(intent);
                }else if(good_allocation_query_val){
                    Intent intent = new Intent();
                    intent.setClass(QueryWayActivity.this,GoodAllocationActivity.class);
                    startActivity(intent);
                }else if(import_query_val){
                    Intent intent = new Intent();
                    intent.setClass(QueryWayActivity.this,ImportQueryActivity.class);
                    startActivity(intent);
                }else if(summary_query_val){
                    Intent intent = new Intent();
                    intent.setClass(QueryWayActivity.this,QueryActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(QueryWayActivity.this,"请选择查询方式",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
