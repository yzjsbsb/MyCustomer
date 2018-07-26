package cn.com.hxx.fakewaterfall.designpattern.observer.observer;

import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/26.
 */

public class User implements Observer {

    private boolean isRegister = false;
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        MyUtils.f(name+"接收到了" + message);
    }

    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }
}
