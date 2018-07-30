package cn.com.hxx.fakewaterfall;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cn.com.hxx.fakewaterfall.CustomerView.CustomerViewActivity;
import cn.com.hxx.fakewaterfall.FourComponents.FourComponentsActivity;
import cn.com.hxx.fakewaterfall.Module.ModuleActivity;
import cn.com.hxx.fakewaterfall.MyAnotation.MyAnotation;
import cn.com.hxx.fakewaterfall.base.BaseActivity;
import cn.com.hxx.fakewaterfall.CustomerView.banner.MyBannerFragment;
import cn.com.hxx.fakewaterfall.CustomerView.customerview.CustomerViewFragment;
import cn.com.hxx.fakewaterfall.CustomerView.customerviewgroup.CustomerViewGroupFragment;
import cn.com.hxx.fakewaterfall.CustomerView.expandview.ExpandFragment;
import cn.com.hxx.fakewaterfall.CustomerView.layoutmanager.CustomLayoutManagerFragemtn;
import cn.com.hxx.fakewaterfall.designpattern.MyDesignPatternActivity;
import cn.com.hxx.fakewaterfall.uti.MyUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.MyConstantUtils;

public class MainActivity extends BaseActivity {

    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyConstantUtils.init();
        initView();
        initButton();
    }

    @Override
    public int setLayoutView() {
        return R.layout.activity_main;
    }

    private void initView() {
        ll_container = findViewById(R.id.ll_container);
    }


    private void initButton() {
        MyUtils.generateButton(ll_container, this, "startMy", 0, null);
    }

    @MyAnotation(order = 1)
    public void startMyCustomerViewActivity(){
        startActivity(new Intent(MainActivity.this, CustomerViewActivity.class));
    }

    @MyAnotation(order = 2)
    public void startMyDesignPatternActivity(){
        startActivity(new Intent(MainActivity.this, MyDesignPatternActivity.class));
    }

    @MyAnotation(order = 3)
    public void startMyFourComponentsActivity(){
        startActivity(new Intent(MainActivity.this, FourComponentsActivity.class));
    }

    @MyAnotation(order = 4)
    public void startMyModuleActivity(){
        startActivity(new Intent(MainActivity.this, ModuleActivity.class));
    }
}
