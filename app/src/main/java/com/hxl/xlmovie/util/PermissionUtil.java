package com.hxl.xlmovie.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理工具
 * Created by Administrator on 2016/6/28.
 */
public class PermissionUtil {

    public static boolean getPermission(final Activity context, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWriteContactsPermission = context.checkSelfPermission(permission);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CONTACTS)) {
                    // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(context, "请开启授权~");
                        }
                    });
                }
                // 申请授权。
                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
                return false;
            }
        }
        return true;
    }

    public static boolean getPermission(final Activity context, String[] permissions, int requestCode) {
        boolean has = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsNeeded = new ArrayList<String>();
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int hasWriteContactsPermission = context.checkSelfPermission(permission);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.show(context, "请开启授权~");
                            }
                        });
                    }
                    permissionsNeeded.add(permission);
                    has = false;
                }
            }
            if (!permissionsNeeded.isEmpty()) {
                // 申请授权。
                ActivityCompat.requestPermissions(context, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), requestCode);
            }
        }
        return has;
    }

    /**
     * 判断用户是全部否授权
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermission(Activity context, String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                int hasWriteContactsPermission = context.checkSelfPermission(permissions[i]);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void getSharePersion(Activity context) {
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(context, mPermissionList, 100);
    }
}
