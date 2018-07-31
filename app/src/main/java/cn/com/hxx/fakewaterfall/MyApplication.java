package cn.com.hxx.fakewaterfall;

import android.app.Application;
import android.util.Log;

/**
 * Created by apple on 2018/7/31.
 */

public class MyApplication extends Application {

    private final String TAG = "application";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "MyApplication onCreate");
    }
}
