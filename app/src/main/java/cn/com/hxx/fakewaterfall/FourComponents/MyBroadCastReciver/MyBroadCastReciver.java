package cn.com.hxx.fakewaterfall.FourComponents.MyBroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/30
 * <p>
 * 本地广播是相对消耗时间、空间最多的一种方式，但是大家都知道，广播是四大组件之一，许多系统级的事件都是通过广播来通知的，比如说网络的变化、电量的变化，短信发送和接收的状态，
 * 所以，如果与android系统进行相关的通知，还是要选择本地广播；在BroadcastReceiver的 onReceive方法中，可以获得Context 、intent参数，
 * 这两个参数可以调用许多的sdk中的方法，而eventbus获得这两个参数相对比较困难；
 * <p>
 * 因此广播相对于其他的方式而言，广播是重量级的，消耗资源较多的方式。他的优势体现在与sdk连接紧密，如果需要同 android 交互的时候，广播的便捷性会抵消掉它过多的资源消耗，
 * 但是如果不同android交互，或者说，只做很少的交互，使用广播是一种浪费；
 */

public class MyBroadCastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case "android.net.conn.CONNECTIVITY_CHANGE":
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager != null){
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()){
                        MyUtils.t(context, "已接入网络");
                    }else {
                        MyUtils.t(context, "网络已断开");
                    }
                }
                break;
            case "BROADCAST_ACTION":
                MyUtils.t(context, "接收到推送");
                break;
        }

    }
}
