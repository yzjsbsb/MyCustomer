package cn.com.hxx.fakewaterfall.FourComponents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import cn.com.hxx.fakewaterfall.FourComponents.MyBroadCastReciver.MyBroadCastReciverFragment;
import cn.com.hxx.fakewaterfall.FourComponents.MyContentProvider.MyContentProviderFragment;
import cn.com.hxx.fakewaterfall.FourComponents.MyService.MyServiceActivity;
import cn.com.hxx.fakewaterfall.MyAnotation.MyAnotation;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;
import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/27.
 */

public class FourComponentsActivity extends BaseActivity {


    FrameLayout fr_container;
    LinearLayout ll_container;
    ScrollView scrollView;

    @Override
    public int setLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    //进入新fragment时候scrollView会隐藏掉，所以调用这个
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            scrollView.setVisibility(View.VISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        scrollView = findViewById(R.id.scrollView);
        ll_container = findViewById(R.id.ll_container);
        fr_container = findViewById(R.id.fr_container);
        MyUtils.generateButton(ll_container, this, "startMy", R.id.fr_container, scrollView);
    }

    @MyAnotation(order = 1)
    public void startMyServiceActivity(){
        startActivity(new Intent(this, MyServiceActivity.class));
    }

    @MyAnotation(order = 2)
    public MyBroadCastReciverFragment startMyBroadCastReciverFragment(){
        return MyBroadCastReciverFragment.getInstance();
    }

    @MyAnotation(order = 3)
    public MyContentProviderFragment startMyContentProviderFragment(){
        return MyContentProviderFragment.getInstance();
    }
}
