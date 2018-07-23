package cn.com.hxx.fakewaterfall.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import cn.com.hxx.fakewaterfall.uti.MyActivityManager;

/**
 * Created by apple on 2018/7/23.
 */

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.getInstatnce(this).addActivity(this);
        setContentView(setLayoutView());
    }

    public abstract int setLayoutView();


    @Override
    public void finish() {
        MyActivityManager.getInstatnce(this).removeActivity(this);
        super.finish();
    }
}
