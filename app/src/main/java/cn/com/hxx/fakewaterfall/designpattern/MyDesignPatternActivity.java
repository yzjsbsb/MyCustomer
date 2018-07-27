package cn.com.hxx.fakewaterfall.designpattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.MyAnotation.MyAnotation;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;
import cn.com.hxx.fakewaterfall.designpattern.observer.ObserverFragment;
import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/26.
 */

public class MyDesignPatternActivity extends BaseActivity {

    FrameLayout fr_container;
    LinearLayout ll_container;
    ScrollView scrollView;

    @Override
    public int setLayoutView() {
        return R.layout.activity_designpattern;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        scrollView = findViewById(R.id.scrollView);
        ll_container = findViewById(R.id.ll_container);
        fr_container = findViewById(R.id.fr_container);
        MyUtils.generateButton(ll_container, this, "Pattern_", R.id.fr_container, scrollView);

    }


    @MyAnotation(order = 1)
    public ObserverFragment Pattern_Observer(){
        return ObserverFragment.getInstatnce();
    }

    @MyAnotation(order = 2)
    public void Pattern_Singleton(){
        MyUtils.t(this, "看MyActivityManager.class");
    }

    @MyAnotation(order = 3)
    public void Pattern_Builder(){
        MyUtils.t(this, "看CustomerPopWindow.class");
    }
}
