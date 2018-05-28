package cn.com.hxx.fakewaterfall.uti.httputil;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.com.hxx.fakewaterfall.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by apple on 2018/5/28.
 */

public class MyOKHttp {

    public static int DEFAULT_TIMEOUT = 15;
    public static OkHttpClient client;

    public static String get(String url) {
        try {
            client = getClient();
            client.newBuilder().connectTimeout(DEFAULT_TIMEOUT * 1000, TimeUnit.MILLISECONDS);
            Request request = new Request.Builder().url(url).build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OkHttpClient getClient() {

        if (client != null) {
            return client;
        }
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //使用拦截器
        httpClientBuilder.addInterceptor(new RequestInterceptor());
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    Log.i("RetrofitLog", "retrofitBack = " + message);
//                    if (StringUtils.startsWith(message, "{\"meta\"")){
//                        try {
//                            JSONObject obj = new JSONObject(message);
//                            MyLogUtils.i("RetrofitLog",obj.toString(4));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    try {
                        JSONObject obj = new JSONObject(message);
                        MyLogUtils.i("RetrofitLog", obj.toString(4));
                    } catch (JSONException e) {
//                        e.printStackTrace();
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }
        client = httpClientBuilder.build();
        return client;
    }

}
