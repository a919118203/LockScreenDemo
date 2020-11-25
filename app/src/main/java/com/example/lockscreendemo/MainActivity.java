package com.example.lockscreendemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.ToastUtils;
import com.example.lockscreendemo.floatview.FloatWindowParamManager;
import com.example.lockscreendemo.floatview.RomUtils;

public class MainActivity extends AppCompatActivity {

    private SwitchCompat switchCompat;
    private MyBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initBroadcastReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }

    private void initView(){
        switchCompat = findViewById(R.id.open_lock_screen);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //如果是androidQ以上  看有没有悬浮窗权限
                    boolean permission = FloatWindowParamManager.checkPermission(MainActivity.this);
                    if(permission || Build.VERSION.SDK_INT < 29){
                        if(RomUtils.isMiuiRom()){
                            if(!XiaomiPermissionUtils.canShowOnLock(MainActivity.this)){
                                XiaomiPermissionUtils.openSettingActivity(MainActivity.this);
                                ToastUtils.showShort("需要锁屏显示权限！");
                                switchCompat.setChecked(false);
                                return ;
                            }

                            if(!XiaomiPermissionUtils.canBackgroundStart(MainActivity.this)){
                                XiaomiPermissionUtils.openSettingActivity(MainActivity.this);
                                ToastUtils.showShort("需要后台弹出界面权限！");
                                switchCompat.setChecked(false);
                                return ;
                            }
                        }
                    }else{
                        FloatWindowParamManager.tryJumpToPermissionPage(MainActivity.this);
                        switchCompat.setChecked(false);
                        return ;
                    }
                }
            }
        });
    }

    private void initBroadcastReceiver(){
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action;
            if(intent != null && (action = intent.getAction()) != null){
                if(action.equals(Intent.ACTION_SCREEN_OFF)){
                    if(!switchCompat.isChecked()){
                        return ;
                    }

                    Intent i = new Intent(MainActivity.this, LockScreenActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }
        }
    }
}