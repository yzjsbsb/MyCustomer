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

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;
import cn.com.hxx.fakewaterfall.designpattern.observer.ObserverFragment;

/**
 * Created by apple on 2018/7/26.
 */

public class MyDesignPatternActivity extends BaseActivity implements View.OnClickListener {

    FrameLayout ll_container;
    Button btn_observer;
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
        btn_observer = findViewById(R.id.btn_observer);
        btn_observer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_observer:
                ObserverFragment observerFragment = ObserverFragment.getInstatnce();
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.ll_container, observerFragment);
                fragmentTransaction.addToBackStack("btn_observer");
                fragmentTransaction.commit();
                break;
        }
    }
}
