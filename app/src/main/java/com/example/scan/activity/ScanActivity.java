package com.example.scan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scan.R;
import com.example.scan.adapter.ScanAdapter;
import com.example.scan.business.ScanBuss;
import com.example.scan.entity.ParameterSetting;
import com.example.scan.entity.ScanSublist;
import com.example.scan.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends Activity {

    private final static String SCAN_ACTION = "urovo.rcv.message";//扫描结束action

    private int SHOW_TIME = 200;
    private Vibrator mVibrator;
    private ScanManager mScanManager;
    private SoundPool soundpool = null;
    private int soundid;
    //扫描出来的条形码数据
    private String barcodeStr;
    private boolean isScaning = false;

    private EditText remark;
    private EditText serialNum;
    private EditText goodsAllocation;
    private ScanBuss scanBuss;
    private boolean fag;
    private ScanAdapter scanAdapter;
    private ListView scan_listView;
    private List<ScanSublist> scanSublistList = new ArrayList<>();
    private TextView totalNum;
    private long totalNum_values;
    private String serialNum_values;
    private Button bt_complete;
    private ParameterSetting parameterSetting;
    private Button confirm;

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            isScaning = false;
            soundpool.play(soundid, 1, 1, 0, 0, 1);
            mVibrator.vibrate(100);
            byte[] barcode = intent.getByteArrayExtra("barocode");
            int barocodelen = intent.getIntExtra("length", 0);
            barcodeStr = new String(barcode, 0, barocodelen);
            scanInsertData();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        initVariable();
        scan_listView.setAdapter(scanAdapter);
        confirmListening();
        totalNum.setText(0 + "");
        initEnterLister();
    }

    private void initScan() {
        mScanManager = new ScanManager();
        mScanManager.openScanner();

        mScanManager.switchOutputMode(0);
        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundid = soundpool.load("/etc/Scan_new.ogg", 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScanManager != null) {
            mScanManager.stopDecode();
            isScaning = false;
        }
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScan();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SCAN_ACTION);
        registerReceiver(mScanReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public void initVariable() {
        scanBuss = new ScanBuss(this);
        remark = findViewById(R.id.et_remark);
        serialNum = findViewById(R.id.et_serialNum);
        goodsAllocation = findViewById(R.id.et_goodsAllocation);
        fag = true;
        scan_listView = findViewById(R.id.lv_scanlist);
        totalNum = findViewById(R.id.tv_total_num);
        bt_complete = findViewById(R.id.bt_complete);
        parameterSetting = scanBuss.paramOne();
        confirm = findViewById(R.id.bt_confirm);
        scanAdapter = new ScanAdapter(scanSublistList, ScanActivity.this);

        bt_complete.setOnClickListener(new CompleteBtListening());
        if (parameterSetting.getNumEnableInput() == 1) {
            scan_listView.setOnItemClickListener(new ListItemClickListener());
        }
    }
    public void initEnterLister() {
        serialNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                goodsAllocation.requestFocus();
                return true;
            }
        });

        goodsAllocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                remark.requestFocus();
                return true;
            }
        });

        remark.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                confirm.requestFocus();
                return true;
            }
        });

    }

    public void confirmListening(){
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                serialNum_values = serialNum.getText().toString().trim();
                if (null == serialNum_values || serialNum_values.equals("")) {
                    Toast.makeText(getApplicationContext(), "编号不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                ScanSublist scanSublist = scanBuss.checkSerNumExist(serialNum_values);
                if (scanSublist != null) {
                    if (parameterSetting.getAginCheck() == 1) {
                        goodsAllocation.setText(scanSublist.getGoodsAllocation());
                        remark.setText(scanSublist.getRemark());

                        scanSublistList = scanBuss.sublistList(serialNum_values, null);
                        scanAdapter.updateList(scanSublistList);
                    } else {
                        Toast.makeText(getApplicationContext(), "该批次已盘点 如想重新盘点扫描 请重新设置参数", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    boolean flag = scanBuss.checkErpSerialNumExist(serialNum_values);
                    if (!flag) {
                        Toast.makeText(getApplicationContext(), "编号必须要与需盘点的批次编号一致", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                serialNum.setEnabled(false);
                goodsAllocation.setEnabled(false);
                remark.setEnabled(false);
                confirm.setEnabled(false);
                totalNum_values = scanBuss.totalNum(serialNum_values);
                totalNum.setText(totalNum_values + "");
            }
        });
    }

    //扫描时插入数据
    public void scanInsertData() {
        if(confirm.isEnabled()){
            Toast.makeText(getApplicationContext(), "请确认/核对您输入的商品信息 确认之后将不能修改", Toast.LENGTH_LONG).show();
            return;
        }

        String goodsAllocation_values = goodsAllocation.getText().toString().trim();
        String remark_values = remark.getText().toString().trim();

        ScanSublist scanSublist = new ScanSublist();
        scanSublist.setSerialNum(serialNum_values);
        scanSublist.setGoodsAllocation(goodsAllocation_values);
        scanSublist.setRemark(remark_values);
        scanSublist.setCommodityCode(barcodeStr);
        scanSublist.setCommodityNum(1);

        if(parameterSetting.getInexistEnableAdd()==1){
            scanBuss.insertSublist(scanSublist);
        }else if(parameterSetting.getInexistEnableAdd()==2){
            boolean codeExist = scanBuss.checkCodeExist(serialNum_values,barcodeStr);
            if(codeExist){
                scanBuss.insertSublist(scanSublist);
            }else{
                final ScanSublist scanSublist1 = scanSublist;
                new AlertDialog.Builder(ScanActivity.this)
                        .setTitle("提示")
                        .setMessage("无此商品,请确认是否要添加此商品")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                scanBuss.insertSublist(scanSublist1);
                                scanSublistList = scanBuss.sublistList(serialNum_values, null);
                                scanAdapter.updateList(scanSublistList);
                                totalNum_values = scanBuss.totalNum(serialNum_values);
                                totalNum.setText(totalNum_values + "");
                            }
                        }).setNegativeButton("取消",null).show();
            }
        }



        scanSublistList = scanBuss.sublistList(serialNum_values, null);
        scanAdapter.updateList(scanSublistList);

        totalNum_values = scanBuss.totalNum(serialNum_values);
        totalNum.setText(totalNum_values + "");
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView adapterView, View view, final int position, long id) {
            final ScanSublist scanSublist = (ScanSublist) adapterView.getAdapter().getItem(position);
            final EditText editNum = new EditText(ScanActivity.this);
            editNum.setHint("请输入要修改的数量");
            editNum.setTextSize(13);
            editNum.setText(scanSublist.getCommodityNum() + "");
            new AlertDialog.Builder(ScanActivity.this)
                    .setTitle("修改商品数量")
                    .setView(editNum)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String editNum_values = editNum.getText().toString().trim();
                            //校验并控制是否关闭
                            if (editNum_values == null || editNum_values.equals("")) {
                                Toast.makeText(getApplicationContext(), "商品数量不能为空", Toast.LENGTH_LONG).show();
                                ReflectUtil.setAlertDialogClose(dialog, false);
                                return;
                            } else if (!editNum_values.matches("[0-9]+")) {
                                Toast.makeText(getApplicationContext(), "数量必须是数字", Toast.LENGTH_LONG).show();
                                ReflectUtil.setAlertDialogClose(dialog, false);
                                return;
                            }
                            scanSublist.setCommodityNum(Integer.parseInt(editNum_values));
                            scanBuss.updateCommNum(scanSublist);
                            scanSublistList.set(position, scanSublist);
                            scanAdapter.updateList(scanSublistList);

                            totalNum_values = scanBuss.totalNum(serialNum_values);
                            totalNum.setText(totalNum_values + "");
                            ReflectUtil.setAlertDialogClose(dialog, true);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ReflectUtil.setAlertDialogClose(dialog, true);
                            return;
                        }
                    }).show();
        }
    }

    private class CompleteBtListening implements OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ScanActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }


//    private class SerNumFocusListener implements View.OnFocusChangeListener {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            if (hasFocus) {
//                goodsAllocation.setEnabled(true);
//                remark.setEnabled(true);
//            } else {
//                String serNum_values = serialNum.getText().toString().trim();
//                if (null != serNum_values && !serNum_values.equals("")) {
//                    ScanSublist scanSublist = scanBuss.checkSerNumExist(serNum_values);
//                    if (scanSublist != null) {
//                        if (parameterSetting.getAginCheck() == 1) {
//                            goodsAllocation.setText(scanSublist.getGoodsAllocation());
//                            remark.setText(scanSublist.getRemark());
//                            goodsAllocation.setEnabled(false);
//                            remark.setEnabled(false);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "该批次已盘点 如想重新盘点扫描 请重新设置参数", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        boolean flag = scanBuss.checkErpSerialNumExist(serialNum.getText().toString());
//                        if (!flag) {
//                            Toast.makeText(getApplicationContext(), "编号必须要与需盘点的批次编号一致", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//
//            }
//        }
//    }

}
