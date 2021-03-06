package com.example.baseinfo.simplebaseinfo;

import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.baseinfo.simplebaseinfo.utils.DeviceUtils;
import com.example.baseinfo.simplebaseinfo.utils.PermissionCheckUtil;
import com.example.baseinfo.simplebaseinfo.utils.ScreenUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        setInfo();
    }

    private void setInfo() {
        String manufacturer = DeviceUtils.getManufacturer();
        String model = DeviceUtils.getModel();
        int sdkVersion = DeviceUtils.getSDKVersion();
        String androidID = DeviceUtils.getAndroidID();
        int screenHeight = ScreenUtils.getScreenHeight();
        int screenWidth = ScreenUtils.getScreenWidth();
        int densityDpi = ScreenUtils.getDensityDpi();
        float density = ScreenUtils.getDensity();
        float saledDensity = ScreenUtils.getsSaledDensity();
        String macAddress = DeviceUtils.getMacAddress();
        StringBuilder infoStr = new StringBuilder(200);
        infoStr.append("手机品牌：").append(manufacturer).append("\n")
                .append("手机型号：").append(model).append("\n")
                .append("系统版本:android " + Build.VERSION.RELEASE)
                .append(" (").append(sdkVersion + ")").append("\n")
                .append("屏幕宽高:").append(screenWidth + "x" + screenHeight).append("\n")
                .append("Dpi:" + densityDpi).append("\n")
                .append("Density:" + density).append("\n")
                .append("SaledDensity:" + saledDensity).append("\n")
                .append("MAC Address:").append(macAddress);
//                .append("android ID:").append(androidID).append("\n");
        mTextView.setText(infoStr.toString());
        PermissionCheckUtil.checkReadPhoneState(this, new Runnable() {
            @Override
            public void run() {
                String deviceIdStr = DeviceUtils.getDeviceIdStr();
                String textStr = mTextView.getText().toString();
                mTextView.setText(textStr + "\n" + "IMEI：" + deviceIdStr);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void reget(View view) {//点击重新获取

        if (b) {
            mTextView.setTextColor(ActivityCompat.getColor(this, R.color.colorAccent));
        } else {
            mTextView.setTextColor(ActivityCompat.getColor(this, R.color.black));
        }
        b = !b;
        setInfo();
    }
}
