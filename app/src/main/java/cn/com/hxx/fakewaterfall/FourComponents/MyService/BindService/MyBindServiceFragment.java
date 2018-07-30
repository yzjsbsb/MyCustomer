package cn.com.hxx.fakewaterfall.FourComponents.MyService.BindService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.com.hxx.fakewaterfall.FourComponents.MyService.StartService.MyStartService;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/7/27.
 */

public class MyBindServiceFragment extends BaseFragment implements View.OnClickListener{

    Button play;
    Button pause;
    Button stop;
    Intent intent;

    MyBindService.MyBinder myBinder;

    public static MyBindServiceFragment getInstance(){
        return new MyBindServiceFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_myservice;
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        //3.系统会调用该方法以传递服务的onBind() 方法返回的 IBinder
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyBindService.MyBinder)service;
        }

        //Android 系统会在与服务的连接意外中断时，例如当服务崩溃或被终止时调用该方法。当客户端取消绑定时，系统不会调用该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindService();
        initView();
    }

    private void bindService() {
        //1.应用组件（客户端）通过调用 bindService() 绑定到服务，绑定是异步的，系统随后调用服务的 onBind() 方法
        Intent intent = new Intent(getContext(), MyBindService.class);
        getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        play = getView().findViewById(R.id.btn_start_play);
        pause = getView().findViewById(R.id.btn_start_pause);
        stop = getView().findViewById(R.id.btn_start_stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        ((TextView)(getView().findViewById(R.id.tv_title))).setText("BindService");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_play :
                myBinder.play();
                break;
            case R.id.btn_start_pause :
                myBinder.pause();
                break;
            case R.id.btn_start_stop :
                myBinder.stop();
                break;
        }
    }

    @Override
    public void onDestroy() {
        getContext().unbindService(serviceConnection);
        super.onDestroy();
    }
}
