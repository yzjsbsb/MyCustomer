package cn.com.hxx.fakewaterfall.FourComponents.MyService.StartService;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/7/27.
 */

public class MyStartServiceFragemtn extends BaseFragment implements View.OnClickListener{

    Button play;
    Button pause;
    Button stop;

    Intent intent;

    @Override
    public int getContentView() {
        return R.layout.fragment_myservice;
    }

    public static MyStartServiceFragemtn getInstance(){
        return new MyStartServiceFragemtn();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        play = getView().findViewById(R.id.btn_start_play);
        pause = getView().findViewById(R.id.btn_start_pause);
        stop = getView().findViewById(R.id.btn_start_stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(getContext(), MyStartService.class);
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.btn_start_play :
                bundle.putString("key", "play");
                break;
            case R.id.btn_start_pause :
                bundle.putString("key", "pause");
                break;
            case R.id.btn_start_stop :
                bundle.putString("key", "stop");
                break;
        }
        intent.putExtras(bundle);
        //通过调用 startService() 启动服务
        getContext().startService(intent);
    }

    //销毁fragment的时候，停止服务，要不然服务会一直存在
    @Override
    public void onDestroy() {
        if (intent != null)getContext().stopService(intent);
        super.onDestroy();
    }
}
