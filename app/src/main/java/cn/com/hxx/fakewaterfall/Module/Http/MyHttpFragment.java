package cn.com.hxx.fakewaterfall.Module.Http;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.com.hxx.fakewaterfall.Module.Http.Utils.AsynConnectionManager;
import cn.com.hxx.fakewaterfall.Module.Http.Utils.MyOkHttpUtils;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/7/31.
 */

public class MyHttpFragment extends BaseFragment {

    private static final String URL = "http://woqi.me/api/home";
    private TextView tv_content;

    @Override
    public int getContentView() {
        return R.layout.fragment_myhttp;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        tv_content = getView().findViewById(R.id.tv_content);
        getView().findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText(" ");
                AsynConnectionManager.get(URL, new AsynConnectionManager.CallBack() {
                    @Override
                    public void onSucces(String reponse) {
                        tv_content.setText(reponse);
                    }
                });
            }
        });

        getView().findViewById(R.id.okhttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText(" ");
                MyOkHttpUtils.get(URL, new MyOkHttpUtils.OkHttpCallBack() {
                    @Override
                    public void onSuccess(String body) {
                        tv_content.setText(body);
                    }
                });
            }
        });
    }
}
