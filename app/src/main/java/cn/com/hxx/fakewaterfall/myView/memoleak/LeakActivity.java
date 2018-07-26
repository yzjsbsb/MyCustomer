package cn.com.hxx.fakewaterfall.myView.memoleak;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.lang.ref.WeakReference;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;

/**
 * Created by apple on 2018/7/23.
 */

public class LeakActivity extends BaseActivity {

    Button btn1;
    WebView webView;
    public static Info sInfo;       //泄漏方式1.静态变量Info会随着类的加载而消失，进程的结束而回收。因此LeakActivity销毁后，sInfo依然存在，其持有了LeakActivity的引用，造成内存泄漏，因此需要去掉static
    private MyHandler myHandler;    //方式2.如果要使用内部类，但又要规避内存泄露，一般都会采用静态内部类+弱引用的方式。onDestroy()时，调用myHandler.removeCallbacksAndMessages(null);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public int setLayoutView() {
        return R.layout.activity_leak;
    }

    private void initView() {
        btn1 = findViewById(R.id.btn1);
        webView = findViewById(R.id.webView);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sInfo == null){
                    sInfo = new Info(LeakActivity.this);
                }
            }
        });

        Message message = Message.obtain();
        message.what = 1;
        myHandler = new MyHandler(this);
        registerReceiver(mReceiver, new IntentFilter());//方式3.在Activity中注册广播，如果在Activity销毁后不取消注册，那么这个刚播会一直存在系统中，同上面所说的非静态内部类一样持有Activity引用，导致内存泄露。因此注册广播后在Activity销毁后一定要取消注册。
    }

    public class Info{

        Activity activity;

        public Info(Activity activity){
            this.activity = activity;
        }

        public Activity getActivity() {
            return activity;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }
    }


    private static class MyHandler extends Handler{
        private WeakReference<LeakActivity> mWeakReference;

        public MyHandler(LeakActivity leakActivity){
            mWeakReference = new WeakReference<LeakActivity>(leakActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            LeakActivity activity = mWeakReference.get();
            if (activity != null){
                //相应逻辑处理
            }
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //逻辑处理
        }
    };

    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        unregisterReceiver(mReceiver);
        //方式4.WebView在加载网页后会长期占用内存而不能被释放。
        //webView父控件移除webView:webViewContainer.removeView(webView);
        webView.stopLoading();
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearHistory();
        webView.removeAllViews();
        webView.destroy();
        super.onDestroy();
    }
}
