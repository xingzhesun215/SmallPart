package com.sun.master;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sun.mylibrary.ProxyActivityInterface;

import java.lang.reflect.Constructor;

public class ProxyActivity extends AppCompatActivity {

    private ProxyActivityInterface pluginObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里拿到了真实跳转的activity 拿出来 再去启动真实的activity

        String className = getIntent().getStringExtra("ClassName");

        //通过反射在去启动一个真实的activity 拿到Class对象
        try {
            Class<?> plugClass = getClassLoader().loadClass(className);
            Constructor<?> pluginConstructor = plugClass.getConstructor(new Class[]{});
            //因为插件的activity实现了我们的标准
            pluginObj = (ProxyActivityInterface) pluginConstructor.newInstance(new Object[]{});
            pluginObj.attach(this);//注入上下文
            pluginObj.onCreate(new Bundle());//一定要调用onCreate
        } catch (Exception e) {
            if (e.getClass().getSimpleName() .equals("ClassCastException")){
                //我这里是直接拿到异常判断的 ，也可的 拿到上面的plugClass对象判断有没有实现我们的接口
                finish();
                Toast.makeText(this,"非法页面",Toast.LENGTH_LONG).show();
                return;
            }
            e.printStackTrace();
        }
    }

    //为什么要重写这个呢 因为这个是插件内部startactivity调用的 将真正要开启的activity的类名穿过来
    //然后取出来，启动我们的占坑的activity 在我们真正要启动的赛进去
    @Override
    public void startActivity(Intent intent) {
        String className1=intent.getStringExtra("ClassName");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("ClassName", className1);
        super.startActivity(intent1);
    }

    //重写classLoader
    @Override
    public ClassLoader getClassLoader() {
        return HookManager.getInstance().getClassLoader();
    }

    //重写Resource
    @Override
    public Resources getResources() {
        return HookManager.getInstance().getResource();
    }

    @Override
    protected void onStart() {
        super.onStart();
        pluginObj.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginObj.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pluginObj.onPause();
    }
}