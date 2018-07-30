package cn.com.hxx.fakewaterfall.FourComponents.MyService;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import cn.com.hxx.fakewaterfall.FourComponents.MyService.BindService.MyBindServiceFragment;
import cn.com.hxx.fakewaterfall.FourComponents.MyService.StartService.MyStartServiceFragemtn;
import cn.com.hxx.fakewaterfall.MyAnotation.MyAnotation;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;
import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/27.
 */

public class MyServiceActivity extends BaseActivity {

    LinearLayout ll_container;
    FrameLayout fr_container;
    ScrollView scrollView;
    @Override
    public int setLayoutView() {
        return R.layout.activity_main;
    }

    //进入新fragment时候scrollView会隐藏掉，所以调用这个
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            scrollView.setVisibility(View.VISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();
    }


    private void initView() {
        ll_container = findViewById(R.id.ll_container);
        fr_container = findViewById(R.id.fr_container);
        scrollView = findViewById(R.id.scrollView);
    }


    private void initButton() {
        MyUtils.generateButton(ll_container, this, "getMy", R.id.fr_container, scrollView);
    }

    @MyAnotation(order = 1)
    public MyStartServiceFragemtn getMyStartServiceFragemtn(){
        return MyStartServiceFragemtn.getInstance();
    }

    @MyAnotation(order = 2)
    public MyBindServiceFragment getMyBindServiceFragemtn(){
        return MyBindServiceFragment.getInstance();
    }
}
