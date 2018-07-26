package cn.com.hxx.fakewaterfall;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cn.com.hxx.fakewaterfall.CustomerView.CustomerViewActivity;
import cn.com.hxx.fakewaterfall.MyAnotation.MyAnotation;
import cn.com.hxx.fakewaterfall.base.BaseActivity;
import cn.com.hxx.fakewaterfall.CustomerView.banner.MyBannerFragment;
import cn.com.hxx.fakewaterfall.CustomerView.customerview.CustomerViewFragment;
import cn.com.hxx.fakewaterfall.CustomerView.customerviewgroup.CustomerViewGroupFragment;
import cn.com.hxx.fakewaterfall.CustomerView.expandview.ExpandFragment;
import cn.com.hxx.fakewaterfall.CustomerView.layoutmanager.CustomLayoutManagerFragemtn;
import cn.com.hxx.fakewaterfall.designpattern.MyDesignPatternActivity;
import cn.com.hxx.fakewaterfall.uti.httputil.MyConstantUtils;

public class MainActivity extends BaseActivity {

    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyConstantUtils.init();
        initView();
        initButton();
    }

    @Override
    public int setLayoutView() {
        return R.layout.activity_main;
    }

    private void initView() {
        ll_container = findViewById(R.id.ll_container);
    }


    private void initButton() {
        //利用反射获取所在类中"start"开头的方法
        List<Method> methodList = new ArrayList<>();
        for (Method method : getClass().getMethods()){
            if (method.getName().startsWith("startMy")){
                methodList.add(method);
            }
        }
        //对methodList进行排序,用以确定button生成对顺序
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            methodList.sort(new Comparator<Method>() {
                @Override
                public int compare(Method o1, Method o2) {
                    MyAnotation annotation1 = o1.getAnnotation(MyAnotation.class);
                    MyAnotation annotation2 = o2.getAnnotation(MyAnotation.class);
                    return annotation1.order() > annotation2.order()? 1 : 0;
                }
            });
        }
        //生成button并利用反射添加button点击效果
        for (final Method method : methodList){
            Button button = new Button(this);
            button.setText(method.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Object invoke = method.invoke(MainActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ll_container.addView(button);
        }
    }

    @MyAnotation(order = 1)
    public void startMyCustomerViewActivity(){
        startActivity(new Intent(MainActivity.this, CustomerViewActivity.class));
    }

    @MyAnotation(order = 2)
    public void startMyDesignPatternActivity(){
        startActivity(new Intent(MainActivity.this, MyDesignPatternActivity.class));
    }
}
