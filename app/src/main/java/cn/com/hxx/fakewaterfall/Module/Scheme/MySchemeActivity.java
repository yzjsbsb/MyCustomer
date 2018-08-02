package cn.com.hxx.fakewaterfall.Module.Scheme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;

/**
 * Created by apple on 2018/8/2.
 * URL Scheme使用场景大致分以下几种：

 服务器下发跳转路径，客户端根据服务器下发跳转路径跳转相应的页面
 H5页面点击锚点，根据锚点具体跳转路径APP端跳转具体的页面
 APP端收到服务器端下发的PUSH通知栏消息，根据消息的点击跳转路径跳转相关页面
 APP根据URL跳转到另外一个APP指定页面

 uri =  scheme://host:port/path;
 scheme:根据这个来跳转到app，如果两个app/activity的scheme一样，则会弹出选择框，选择打开哪个app
 */

public class MySchemeActivity extends BaseActivity {

    TextView tv_data;

    @Override
    public int setLayoutView() {
        return R.layout.activity_myscheme;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    //Uri = "myschememe://www.hxxchina.com:8080/mypath?key=mykey"
    private void initView() {
        tv_data = findViewById(R.id.tv_data);
        Uri uri = getIntent().getData();
        if (uri != null){
            String scheme = uri.getScheme();
            String authority = uri.getAuthority();
            String path = uri.getPath();
            tv_data.setText("scheme = " + scheme + "| authority = " + authority + " | path = " + path);
        }
    }

    @Override
    public void finish() {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("result", "My name is linjiqin");
        //设置返回数据
        this.setResult(RESULT_OK, intent);
        super.finish();
    }
}
