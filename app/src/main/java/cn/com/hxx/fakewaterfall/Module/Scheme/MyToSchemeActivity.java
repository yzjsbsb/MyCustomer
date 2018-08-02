package cn.com.hxx.fakewaterfall.Module.Scheme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;

/**
 * Created by apple on 2018/8/2.
 */

public class MyToSchemeActivity extends BaseActivity {

    private TextView tv_data_return;

    @Override
    public int setLayoutView() {
        return R.layout.activity_mytoscheme;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_toscheme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过scheme跳转activity:必须在某个activity中intent-filter里有myschememe才会跳转到对应activity
                Uri uri = Uri.parse("myschememe://www.hxxchina.com:8080/mypath?key=mykey");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //保证新启动的APP有单独的堆栈，如果希望新启动的APP和原有APP使用同一个堆栈则去掉该项
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                /***
                 * startActivityForResult比startActivity会多一个返回intent。当关闭跳转activity后，会回调MyToSchemeActivity的onActivityResult
                 * 因此需要重载(int requestCode, int resultCode, Intent data)，
                 * requestCode是startActivityForResul()里的code
                 * data则是从前一个activity传过来的intent，具体写法见MySchemeActivity
                 */
                if (hasScheme(uri)){
                    startActivityForResult(intent, 22);
                }else {
                    Toast.makeText(MyToSchemeActivity.this, "没有匹配的APP，请下载安装", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_data_return = findViewById(R.id.tv_data_return);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //判断Uri是否有对应的Acitivty
    private boolean hasScheme(Uri uri){
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        if (!resolveInfos.isEmpty()){
            return true;
        }
        return false;
    }
}
