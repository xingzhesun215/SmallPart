package com.sun.chajian;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.sun.mylibrary.ProxyActivityInterface;

// 这是插件的基类，所有的activity都要继承这个类，
public class BaseActivity extends Activity implements ProxyActivityInterface {
    public Activity that;//这里的that 指的是我们的宿主app，因为插件是没有安装的 是没有上下文的

    @Override
    public void attach(Activity proxyActivity) {
        that = proxyActivity;
    }

    @Override
    public void setContentView(View view) {//最终调用宿主的activity
        if (that != null) {
            that.setContentView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        that.setContentView(layoutResID);
    }

    @Override
    public View findViewById(int id) {
        return that.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        if (that != null) {
            return that.getIntent();
        }
        return super.getIntent();
    }

    @Override
    public ClassLoader getClassLoader() {
        return that.getClassLoader();
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return that.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return that.getApplicationInfo();
    }

    @Override
    public Window getWindow() {
        return that.getWindow();
    }


    @Override
    public WindowManager getWindowManager() {
        return that.getWindowManager();
    }

    @Override
    public void startActivity(Intent intent) {
//        ProxyActivity --->className
        Intent m = new Intent();
        m.putExtra("ClassName", intent.getComponent().getClassName());
        that.startActivity(m);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }


    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return that.registerReceiver(receiver, filter);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        that.unregisterReceiver(receiver);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        that.sendBroadcast(intent);
    }
}