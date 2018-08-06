package com.example.scan.business;

import android.content.Context;

import com.example.scan.dao.UserInfoDAOImpl;
import com.example.scan.entity.UserInfo;

public class UserInfoBuss {
    private UserInfoDAOImpl userInfoDAO;
    private Context context;

    public  UserInfoBuss(Context mcontext) {
        this.context = mcontext;
        userInfoDAO = new UserInfoDAOImpl(mcontext);
    }

    //注册
    public void registerUser(UserInfo userInfo){
        userInfoDAO.registerUser(userInfo);
    }
    //登录
    public boolean loginUser(String userName,String password){
        return userInfoDAO.loginUser(userName,password);
    }
    //查看用户名是否已存在
    public boolean checkUserExist(String userName){
        return userInfoDAO.checkUserNameExist(userName);
    }
}
