package com.sun.chajian;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.sun.mylibrary.ProxyBroadCastInterface;

public class MyReceive extends BroadcastReceiver implements ProxyBroadCastInterface {
    @Override
    public void attch(Context context) {
        // 广播绑定成功
        Toast.makeText(context, "广播绑定成功", Toast.LENGTH_LONG).show();
        Log.e("ME", " 广播绑定成功");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //接受到广播
        Toast.makeText(context, " 接受到广播", Toast.LENGTH_LONG).show();
        Log.e("ME", " 接受到广播");
    }
}
