package cn.com.hxx.fakewaterfall.FourComponents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import cn.com.hxx.fakewaterfall.FourComponents.MyBroadCastReciver.MyBroadCastReciverFragment;
import cn.com.hxx.fakewaterfall.FourComponents.MyService.MyServiceActivity;
import cn.com.hxx.fakewaterfall.MyAnotation.MyAnotation;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;
import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/27.
 */

public class FourComponentsActivity extends BaseActivity {

    LinearLayout ll_container;

    @Override
    public int setLayoutView() {
        return R.layout.activity_fourcomponents;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        ll_container = findViewById(R.id.ll_container);
        MyUtils.generateButton(ll_container, this, "startMy",R.id.ll_container, null);
    }

    @MyAnotation(order = 1)
    public void startMyServiceActivity(){
        startActivity(new Intent(this, MyServiceActivity.class));
    }

    @MyAnotation(order = 1)
    public MyBroadCastReciverFragment startMyBroadCastReciverFragment(){
        return MyBroadCastReciverFragment.getInstance();
    }
}
