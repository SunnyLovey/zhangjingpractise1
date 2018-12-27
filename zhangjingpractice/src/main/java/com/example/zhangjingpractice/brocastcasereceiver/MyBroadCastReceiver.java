package com.example.zhangjingpractice.brocastcasereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.zhangjingpractice.activity.DetailActivity;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //跳转到详情页
        if(intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")){
            Intent intent1=new Intent(context, DetailActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent1);
        }
    }
}
