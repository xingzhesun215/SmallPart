package com.sun.mylibrary;

import android.content.Context;
import android.content.Intent;

public interface ProxyBroadCastInterface {

    void attch(Context context);

    void onReceive(Context context, Intent intent);
}