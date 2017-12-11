package com.example.baseinfo.simplebaseinfo;

import android.app.Application;

import com.example.baseinfo.simplebaseinfo.utils.Utils;

/**
 * 时间： 2017.12.06
 * 作者： hcs
 * 描述说明：
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
