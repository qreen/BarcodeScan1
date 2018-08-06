package com.example.scan.util;

import android.content.DialogInterface;

import java.lang.reflect.Field;

public class ReflectUtil {
    //反射方式获取alertDialog的mShowing静态私有方法，从而设置alertDialog的开关
    public static void setAlertDialogClose(DialogInterface dialog,Boolean pIsClose){
        try {
            Field _Field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            _Field.setAccessible(true);
            _Field.set(dialog,pIsClose);
        }catch (Exception e){

        }
    }
}
