package cn.com.hxx.fakewaterfall.Module.Http.Utils;

import android.content.Context;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by apple on 2018/7/31.
 */

public class MyOkHttpUtils {

    public interface OkHttpCallBack{
        void onSuccess(String body);
    }

    public static void get(String path, final OkHttpCallBack callBack){
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            //Request：构建请求参数，如url，请求方式，请求参数，header等
            final Request request = new Request.Builder()
                    .url(path)
                    .method("GET", null)
                    .build();
            final Call call = okHttpClient.newCall(request);
            if (true){
                //call.execute()是非异步方式，会阻塞线程，等待返回结果，因此将其放入新开线程中
                final Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response execute = call.execute();
                            if (execute.code() == 200){
                                ResponseBody body = execute.body();
                                InputStream inputStream = body.byteStream();
                                final String content = getStringFromInputStream(inputStream);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.onSuccess(content);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else {
                //call.enqueue();是异步的,Callback的onFailure()及onResponse()运行在非主线程，不能在该方法中直接进行UI操作。
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String content = getStringFromInputStream(response.body().byteStream());
                    }
                });
            }

        }catch (Exception e){

        }finally {

        }
    }

    public static void post(String path){
        //POST请求跟Get基本相同，需要增加RequestBody来存储请求的参数信息；在Request.Builder中增加post(RequestBody)调用。
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("key", "value")
                .build();
        Request request = new Request.Builder()
                .url(path)
                .method("POST", null)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    private static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len  = is.read(buffer)) != -1){
            ba.write(buffer, 0 , len);
        }
        is.close();
        String content = ba.toString();
        ba.close();
        return content;
    }
}
