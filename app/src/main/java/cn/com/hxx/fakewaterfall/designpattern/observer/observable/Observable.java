package cn.com.hxx.fakewaterfall.designpattern.observer.observable;

import cn.com.hxx.fakewaterfall.designpattern.observer.observer.Observer;

/**
 * Created by apple on 2018/7/26.
 */

public interface Observable {
    //被观察者的接口
    void register(Observer observer);
    void unregister(Observer observer);
    void notifyObserver();
}
