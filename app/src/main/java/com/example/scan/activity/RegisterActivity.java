package com.example.scan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scan.R;
import com.example.scan.business.UserInfoBuss;
import com.example.scan.entity.UserInfo;

public class RegisterActivity extends Activity {
    private Button register;
    private Button login;
    private EditText userName;
    private EditText password;
    private EditText affirm;
    private UserInfoBuss userInfoBuss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initVariable();
        initLister();
    }


    public void initVariable(){
        register = findViewById(R.id.bt_reg_register);
        login = findViewById(R.id.bt_reg_login);
        userName = findViewById(R.id.bt_reg_username);
        password = findViewById(R.id.bt_reg_password);
        affirm = findViewById(R.id.bt_reg_affirm_password);
        userInfoBuss = new UserInfoBuss(RegisterActivity.this);
    }

    public void initLister(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDispose();
            }
        });
        affirm.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    registerDispose();
                }
                return false;
            }
        });
    }

    public void registerDispose(){
        String username_val = userName.getText().toString().trim();
        String password_val = password.getText().toString().trim();
        String affirm_val = affirm.getText().toString().trim();
        if(null == username_val || username_val.equals("")){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(null == password_val || password_val.equals("")){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if (!password_val.matches("[a-zA-Z0-9]*")){
            Toast.makeText(RegisterActivity.this,"密码只能由数字和字母组成",Toast.LENGTH_LONG).show();
            password.setText("");
            return;
        }
        if(null == affirm_val || affirm_val.equals("")){
            Toast.makeText(RegisterActivity.this,"确认密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(!affirm_val.equals(password_val)){
            Toast.makeText(RegisterActivity.this,"确认密码与密码不一致",Toast.LENGTH_LONG).show();
            affirm.setText("");
            return;
        }

        boolean flag = userInfoBuss.checkUserExist(username_val);
        if(flag){
            Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_LONG).show();
            return;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(username_val);
        userInfo.setPassword(password_val);
        userInfoBuss.registerUser(userInfo);

        userName.setText("");
        password.setText("");
        affirm.setText("");

        new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("提示")
                .setMessage("注册成功啦，现在就去登录?")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this,UserActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消",null).show();
    }
}
