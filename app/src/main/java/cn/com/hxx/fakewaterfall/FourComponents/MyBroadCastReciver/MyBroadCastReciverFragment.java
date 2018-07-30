package cn.com.hxx.fakewaterfall.FourComponents.MyBroadCastReciver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/7/30.
 */

public class MyBroadCastReciverFragment extends BaseFragment {
    @Override
    public int getContentView() {
        return R.layout.broadcastreciver_my;
    }

    public static MyBroadCastReciverFragment getInstance(){
        return new MyBroadCastReciverFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        getView().findViewById(R.id.btn_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //设置对应BroadcastReceiver中intentFilter的action
                intent.setAction("BROADCAST_ACTION");
                //发送广播
                getContext().sendBroadcast(intent);
            }
        });
    }
}
