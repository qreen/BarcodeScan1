package com.example.scan.activity;

import android.app.Activity;
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

public class UserActivity extends Activity {
    private EditText userName;
    private EditText password;
    private Button login;
    private UserInfoBuss userInfoBuss;
    private Button register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initVariable();
        initLister();
    }

    private void initVariable(){
        userInfoBuss = new UserInfoBuss(UserActivity.this);
        userName = findViewById(R.id.et_userName);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.bt_register);
    }

    private void initLister(){
        userName.setFocusable(true);
        userName.setFocusableInTouchMode(true);
        userName.requestFocus(); // 初始不让EditText得焦点
        userName.requestFocusFromTouch();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDispose();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UserActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    loginDispose();
                }
                return false;
            }
        });
    }

    public void loginDispose(){
        String userName_values = userName.getText().toString().trim();
        if(null == userName_values || userName_values.equals("")){
            Toast.makeText(UserActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        String password_values = password.getText().toString().trim();
        if(null == password_values || password_values.equals("")){
            Toast.makeText(UserActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
            password.setText("");
            return;
        }
        if (!password_values.matches("[a-zA-Z0-9]*")){
            Toast.makeText(UserActivity.this,"密码只能由数字和字母组成",Toast.LENGTH_LONG).show();
            return;
        }

        boolean flag = userInfoBuss.loginUser(userName_values,password_values);
        if(flag){
            Intent intent = new Intent();
            intent.setClass(UserActivity.this,HomeActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(UserActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
            password.setText("");
        }
    }

}
