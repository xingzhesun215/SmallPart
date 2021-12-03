package com.sun.chajian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {


    private MyReceive myReceive;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //这里是启动第二个activity
        findViewById(R.id.mBtnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, SecondActivity.class);
                //这里其实调用父类的 最终会调用宿主里面的startActivity方法，下面会对其进行重写
                startActivity(intent);
            }
        });
        findViewById(R.id.mRegiestBroadCast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myReceive=new MyReceive();
                IntentFilter intent = new IntentFilter();
                intent.addAction("com.plugin.app.receive");
                //调用register方法 肯定要调用宿主的 所以重写baseactivity
                registerReceiver(myReceive, intent);
            }
        });

        findViewById(R.id.mSendBroadCast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.plugin.app.receive");
                sendBroadcast(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(myReceive!=null){
            unregisterReceiver(myReceive);
        }
    }
}