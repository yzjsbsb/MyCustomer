package cn.com.hxx.fakewaterfall.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import cn.com.hxx.fakewaterfall.designpattern.singleton.MyActivityManager;

/**
 * Created by apple on 2018/7/23.
 */

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager.getInstance().addActivity(this);
        setContentView(setLayoutView());
    }

    public abstract int setLayoutView();


    @Override
    public void finish() {
        MyActivityManager.getInstance().removeActivity(this);
        super.finish();
    }
}
