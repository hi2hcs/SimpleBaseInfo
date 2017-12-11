package com.example.baseinfo.simplebaseinfo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 以后请用这个吐司，谢谢！！！
 * <p>
 * <p>
 * {@link Toast}的创建都是要inflate一个layout, findViewById之类的
 * 将一个吐司单例化，并且作防止频繁点击的处理。
 * <p>
 */
@SuppressWarnings("all")
public class SimplexToast {

    private static Toast mToast;
    private static long nextTimeMillis;
    private static int yOffset;

    private SimplexToast(Context context) {

    }

    public static Toast init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context should not be null!!!");
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            yOffset = mToast.getYOffset();
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, yOffset);
        mToast.setMargin(0, 0);
        return mToast;
    }

    public static void show(String content) {
        if (!TextUtils.isEmpty(content)) {
            show(content, Toast.LENGTH_SHORT);
        }
    }

    public static void show(String content, int duration) {
        show(content, Gravity.BOTTOM, duration);
    }

    public static void show(int rid) {
        if (rid != 0) {
            show(Utils.getApp().getResources().getString(rid));
        }
    }

    public static void show(int gravity, String content) {
        show(content, gravity, Toast.LENGTH_SHORT);
    }

    public static void show(String content, int gravity, int duration) {
        long current = System.currentTimeMillis();
        //if (current < nextTimeMillis) return;
        if (mToast == null) init(Utils.getApp().getApplicationContext());
        mToast.setText(content);
        mToast.setDuration(duration);
        mToast.setGravity(gravity, 0, yOffset);
        nextTimeMillis = current + (duration == Toast.LENGTH_LONG ? 3500 : 2000);
        mToast.show();
    }
}
