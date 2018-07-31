package cn.com.hxx.fakewaterfall.Module.Http.Utils;

import android.os.Handler;

import java.net.HttpURLConnection;

/**
 * Created by apple on 2018/7/31.
 * 每次都new Thread，new Handler消耗过大
 * 没有异常处理机制
 * 没有缓存机制
 * 没有完善的API(请求头,参数,编码,拦截器等)与调试模式
 * 没有Https
 */

public class AsynConnectionManager {

    public static void get(final String url, final CallBack callBack){

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String content = HttpURLConnectionUtils.get(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //在主线程进行操作
                        callBack.onSucces(content);
                    }
                });
            }
        }).start();
    }

    public static void post(final String url, final String data, final CallBack callBack){

        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                final String response = HttpURLConnectionUtils.post(url, data);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSucces(response);
                    }
                });
            }
        }).start();
    }


    public interface CallBack{
        public void onSucces(String reponse);
    }
}
