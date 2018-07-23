package cn.com.hxx.fakewaterfall.uti;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2018/7/23.
 */

public class MyActivityManager {

    public static MyActivityManager mActivityManager;
    public static List<Activity> mList;
    private Context mContext;

    public MyActivityManager(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    //单利模式，静态特性使得它的生命周期同应用的生命周期一样长。因此context需要转换成applicationContext，否则各个Activity的引用依然存在
    public static MyActivityManager getInstatnce(Context context){
        if (mActivityManager == null){
            mActivityManager = new MyActivityManager(context.getApplicationContext());
        }
        return mActivityManager;
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
