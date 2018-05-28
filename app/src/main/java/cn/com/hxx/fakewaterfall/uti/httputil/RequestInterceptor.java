package cn.com.hxx.fakewaterfall.uti.httputil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by apple on 2018/5/28.
 */

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String umeng_channel = "origin";
        String token = "";

        Request request = chain.request()
                .newBuilder()
                .addHeader("Accept-Version", "v1_7")
                .addHeader("appversion", "2.3.0")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", token)
                .addHeader("platform", "android")
                .addHeader("channel", umeng_channel)
//                .addHeader("Accept-Encoding", "*")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Access-Control-Allow-Origin", "*")
//                .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
//                .addHeader("Vary", "Accept-Encoding")
//                .addHeader("Cookie", "add cookies here")
                .build();

//        long t1 = System.nanoTime();
//        Logger.d(String.format("Sending request %s on %s%n%s",
//                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
//        不管有没有loading都调用hide 不会错
        MyHttpUtils.hideLoading();
//        if (response.code() != MyHttpUtils.OK){
//            throw new IOException(response.message());
//        }
//        long t2 = System.nanoTime();
//        Logger.d(String.format("Received response for %s in %.1fms%n%s",
//                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;

    }
}
