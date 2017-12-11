package com.example.baseinfo.simplebaseinfo.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 *
 *
 * 对动态权限请求逻辑进行封装
 */

public class PermissionCheckUtil {
    //检查拍照权限
    public static void checkCamera(Activity activity, final Runnable runnable) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
            boolean isAccept = true;

            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && permission.granted && isAccept) {
                    runnable.run();
                } else {
                    if (!permission.granted) {
                        isAccept = false;
                        if (permission.name.equals(Manifest.permission.CAMERA)) {
                            SimplexToast.show("您拒绝了打开相机的权限,操作失败");
                        } else {
                            SimplexToast.show("您拒绝了保存图片的权限,操作失败");
                        }
                    }
                }
            }
        });
    }

    //检查录视频权限
    public static void checkRecordVideo(Activity activity, final Runnable runnable, final Dialog loadingDialog) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
            boolean isAccept = true;

            @Override
            public void accept(Permission permission) throws Exception {
                //Log.e("权限: ", "请求");
                if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && permission.granted && isAccept) {
                    runnable.run();
                } else {
                    if (!permission.granted) {
                        isAccept = false;
                        if (permission.name.equals(Manifest.permission.CAMERA)) {
                            SimplexToast.show("您拒绝了打开摄像头的权限,操作失败");
                        } else {
                            SimplexToast.show("您拒绝了录音的权限,操作失败");
                        }
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                }
            }
        });
    }

    //没有完全授权就会弹出去设置的弹框
    public static void checkRecordVideoWithDialog(final Activity activity, final Runnable runnable) {
        RxPermissions rxPermissions = new RxPermissions(activity);

        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
            boolean isAccept = true;
            int sum = 0;

            @Override
            public void accept(Permission permission) throws Exception {
                //Log.e("权限: ", "请求");
                if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && permission.granted && isAccept) {
                    runnable.run();
                } else {
                    sum++;
                    if (permission.shouldShowRequestPermissionRationale) {
                        isAccept = false;
                        //会重新弹出,不处理
                    } else if (!permission.granted) {
                        //拒绝了不会重新弹出
                        isAccept = false;
                    }
                    if (sum >= 3 && !isAccept) {//到最后一个权限且没有完全授权
                        showSetPermissionDialog(activity, "录制视频需要授权相机、麦克风及存储权限", false);
                    }
                }
            }
        });
    }

    public static void showSetPermissionDialog(final Activity activity, final String permissiondes, final boolean isNeedFinishActivity) {
        if (activity == null) {
            return;
        }
//        UIThread.getInstance().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                CommonTwoBtnTipsDialogFragment dialogFragment = showTwoBtnDialog(activity);
//                if (dialogFragment != null) {
//                    if (isNeedFinishActivity) {
//                        dialogFragment.setCanceledOnTouchOutside(false);
//                    }
//                    dialogFragment.setTips(permissiondes + ",请到[应用详情] > [权限]页面授权");
//                    dialogFragment.setCancelBtnText("取消");
//                    dialogFragment.setConfirmBtnText("去设置");
//                    dialogFragment.setTwoBtnOnClickListener(new TwoBtnOnClickListener() {
//                        @Override
//                        public void onCancelClick() {
//                            if (isNeedFinishActivity) {
//                                activity.finish();
//                            }
//                        }
//
//                        @Override
//                        public void onConfirmClick() {
//                            try {
//                                Uri packageURI = Uri.parse("package:" + activity.getPackageName());
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                                activity.startActivity(intent);
//                                if (isNeedFinishActivity) {
//                                    activity.finish();
//                                }
//                            } catch (Exception e) {//预防某些改造的手机没有这个act
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }

    //检查录音频权限
    public static void checkRecordAudio(final Activity activity, final Runnable runnable) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
            boolean isAccept = true;

            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && permission.granted && isAccept) {
                    runnable.run();
                } else {
                    if (permission.shouldShowRequestPermissionRationale) {
                        isAccept = false;
                        //会重新弹出,不处理
                    } else if (!permission.granted) {
                        //拒绝了不会重新弹出
                        isAccept = false;
                        showSetPermissionDialog(activity, "录制语音需要授权麦克风及存储权限", false);
                    }
                }
            }
        });
    }

//    private static CommonTwoBtnTipsDialogFragment showTwoBtnDialog(Activity activity) {
//        return (CommonTwoBtnTipsDialogFragment) DialogFragmentUtil
//                .showMyDialog(activity.getFragmentManager(), CommonTwoBtnTipsDialogFragment.class);
//    }

    //检查定位权限
    public static void checkLocation(Activity activity, final Runnable runnable) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Permission>() {
            boolean isAccept = true;

            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                        && permission.granted && isAccept) {
                    runnable.run();
                } else {
                    if (!permission.granted) {
                        isAccept = false;
//                        DataManager.getInstance().setCityName("未知");
                    }
                }
            }
        });
    }

    public static void checkReadPhoneState(Activity activity, final Runnable runnable) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    runnable.run();
                } else {
                    SimplexToast.show("您拒绝了读取手机硬件信息权限,操作失败");
                }
            }
        });
    }

    public static void checkWriteExStorage(Activity activity, final Runnable runnable) {
        if (activity == null || runnable == null) {
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    runnable.run();
                } else {
                    SimplexToast.show("您拒绝了保存文件的权限,操作失败");
                }
            }
        });
    }

    public static void checkFloatWindow(Activity activity, final Runnable runnable) {
        if (activity == null || runnable == null) {
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.SYSTEM_ALERT_WINDOW)
                .subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    runnable.run();
                } else {
                    SimplexToast.show("您拒绝了显示悬浮窗,操作失败");
                }
            }
        });
    }

    public static void check1v1VideoCall(Activity activity, final Runnable runnable, final Runnable runnableFail) {
        if (activity == null || runnable == null) {
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA).subscribe(new Consumer<Permission>() {
            boolean isAccept = true;

            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted && isAccept) {
                    if (permission.name.equals(Manifest.permission.CAMERA)) {
                        runnable.run();
                    }
                } else {
                    isAccept = false;
                    if(permission.name.equals(Manifest.permission.CAMERA)){
                        runnableFail.run();
                    }
                }
            }
        });
    }
}
