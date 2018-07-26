package cn.com.hxx.fakewaterfall.designpattern.observer.observable;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.designpattern.observer.observer.Observer;

/**
 * Created by apple on 2018/7/26.
 */

public class GongZhongHao implements Observable {

    private List<Observer> list;
    private String message;

    public GongZhongHao() {
        list = new ArrayList<>();
    }

    @Override
    public void register(Observer observer) {
        if (list != null) list.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        if (list != null) list.remove(observer);

    }

    @Override
    public void notifyObserver() {
        for (int i = 0; i < list.size(); i++){
            list.get(i).update(message);
        }
    }

    public void publish(String str){
        message = str;
        notifyObserver();
    }
}
