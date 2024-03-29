package com.sun.master;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sun.mylibrary.ProxyBroadCastInterface;

import java.lang.reflect.Constructor;

public class ProxyReceive extends BroadcastReceiver {
    String className;
    private ProxyBroadCastInterface receiveObj;

    public ProxyReceive(String className, Context context) {
        this.className = className;
        //这里通过classname 得到class对象，然后
        try {
            Class<?> receiverClass = HookManager.getInstance().getClassLoader().loadClass(className);
            Constructor constructorReceiver = receiverClass.getConstructor(new Class[]{});
            receiveObj = (ProxyBroadCastInterface) constructorReceiver.newInstance(new Object[]{});
            receiveObj.attch(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        receiveObj.onReceive(context, intent);
    }
}