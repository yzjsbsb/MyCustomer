package cn.com.hxx.fakewaterfall.designpattern.observer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;
import cn.com.hxx.fakewaterfall.designpattern.observer.observable.GongZhongHao;
import cn.com.hxx.fakewaterfall.designpattern.observer.observer.User;
import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/26.
 */

public class ObserverFragment extends BaseFragment implements View.OnClickListener {

    private GongZhongHao gongZhongHao;
    private User xiaoming;
    private User xiaohong;
    private User xiaoji;
    Button btn_xiaoming;
    Button btn_xiaohong;
    Button btn_xiaoji;
    Button btn_publish;

    public static ObserverFragment getInstatnce(){
        return new ObserverFragment();
    }

    @Override
    public int getContentView() {
        return R.layout.observerfragment_layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        gongZhongHao = new GongZhongHao();
        xiaoming = new User("小明");
        xiaohong = new User("小红");
        xiaoji = new User("小鸡");
        btn_xiaoming = getActivity().findViewById(R.id.btn_xiaoming);
        btn_xiaohong = getActivity().findViewById(R.id.btn_xiaohong);
        btn_xiaoji = getActivity().findViewById(R.id.btn_xiaoji);
        btn_publish = getActivity().findViewById(R.id.btn_publish);

        btn_xiaoming.setOnClickListener(this);
        btn_xiaohong.setOnClickListener(this);
        btn_xiaoji.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_xiaoming:
                MyUtils.t(getContext(), xiaohong.isRegister() ? "已取消注册" : "已注册");
                if (xiaoming.isRegister()){
                    gongZhongHao.unregister(xiaoming);
                }else {
                    gongZhongHao.register(xiaoming);
                }
                xiaoming.setRegister(!xiaoming.isRegister());
                break;
            case R.id.btn_xiaohong:
                MyUtils.t(getContext(), xiaohong.isRegister() ? "已取消注册" : "已注册");
                if (xiaohong.isRegister()){
                    gongZhongHao.unregister(xiaohong);
                }else {
                    gongZhongHao.register(xiaohong);
                }
                xiaohong.setRegister(!xiaohong.isRegister());
                break;
            case R.id.btn_xiaoji:
                MyUtils.t(getContext(), xiaoji.isRegister() ? "已取消注册" : "已注册");
                if (xiaoji.isRegister()){
                    gongZhongHao.unregister(xiaoji);
                }else {
                    gongZhongHao.register(xiaoji);
                }
                xiaoji.setRegister(!xiaoji.isRegister());
                break;
            case R.id.btn_publish:
                gongZhongHao.publish("我的天啊，哈哈哈");
                break;
        }
    }
}
