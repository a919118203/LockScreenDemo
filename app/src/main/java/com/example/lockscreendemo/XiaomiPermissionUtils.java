package com.example.lockscreendemo;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.lang.reflect.Method;

public class XiaomiPermissionUtils {
    public static void openSettingActivity(Context context){
        Intent settingIntent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        settingIntent.setData(Uri.parse("package:" + context.getPackageName()));
        settingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 小米手机检查是否有后台弹出界面权限
     * @return
     */
    public static boolean canBackgroundStart(Context context) {
        AppOpsManager ops = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                int op = 10021; // >= 23
                // ops.checkOpNoThrow(op, uid, packageName)
                Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]
                        {int.class, int.class, String.class}
                );
                Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), context.getPackageName());
                return result == AppOpsManager.MODE_ALLOWED;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 小米手机检查是否有锁屏显示权限
     * @return
     */
    public static boolean canShowOnLock(Context context) {
        AppOpsManager ops = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                int op = 10020; // >= 23
                // ops.checkOpNoThrow(op, uid, packageName)
                Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]
                        {int.class, int.class, String.class}
                );
                Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), context.getPackageName());
                return result == AppOpsManager.MODE_ALLOWED;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
