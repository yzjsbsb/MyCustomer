package cn.com.hxx.fakewaterfall.designpattern.singleton;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2018/7/23.
 */

public class MyActivityManager {

    public static List<Activity> mList;

    public MyActivityManager() {
        mList = new ArrayList<>();
    }


    //使用内部类的方式进行单例初始化，这种方式避免了线程不安全的问题.
    //1.静态内部类方式在MyActivityManager类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，从而完成MyActivityManager的实例化。
    //2.类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。
    private static class MyActivityManagerInner{
        private static MyActivityManager myActivityManager = new MyActivityManager();
    }
    public static MyActivityManager getInstance(){
        return MyActivityManagerInner.myActivityManager;
    }

    public Activity getTopActivityInStack(){
        if (mList.size() != 0){
            return mList.get(mList.size() - 1);
        }else {
            return null;
        }
    }

    public void addActivity(Activity activity){
        mList.add(activity);
    }

    public void removeActivity(Activity activity){
        if (mList.contains(activity)){
            mList.remove(activity);
        }
    }
}
