package cn.com.hxx.fakewaterfall.uti.httputil;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apple on 2018/5/28.
 */

public abstract class ICallback<T> implements Callback<T> {

    //    上次触发登录的时间
    private static long lastCallTime = System.currentTimeMillis();
    private static int minCallIntervalTime = 3000;

    public ICallback() {

    }

    public abstract void onSuccess(T result) throws Exception;

    //    public abstract void onFailed(Call<T> call, Throwable t);
//
    public void onFinally() {

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            if (response.isSuccessful()) {
                if (response.body() instanceof HttpResult) {
                    HttpResult result = (HttpResult) response.body();
                    if (result != null && result.getMeta() != null && result.getMeta().getStatus() == MyHttpUtils.OK) {
                        onSuccess(response.body());
                    } else {
                        if (result != null && result.getMeta() != null && result.getMeta().getMessage() != null) {
                            onFailure(call, new IOException(result.getMeta().getMessage()));
                        } else {
                            onFailure(call, new IOException(response.code() + ""));
                        }
                    }
                } else {
                    onSuccess(response.body());
                }
            } else {
                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;
                try {
                    jsonObject = new JSONObject(response.errorBody().string());
                    jsonData = jsonObject.getString("meta");
                    MetaResult meta = gson.fromJson(jsonData, new TypeToken<MetaResult>() {
                    }.getType());
                    onFailure(call, new IOException(meta.getMessage()));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (Exception e) {
            onFailure(call, new IOException(response.code() + ""));
            e.printStackTrace();
        } finally {
            onFinally();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        String msg = t.getMessage();
        if ("unauthorized!".equalsIgnoreCase(msg) && System.currentTimeMillis() - lastCallTime > minCallIntervalTime) {
            //token失效时，返回unauthorized!清除数据，让用户重新登录
        }
        boolean found = false;
        String[] regex = {
                ".*sample/sample",
        };
        for (int i = 0; i < regex.length; i++) {
            Pattern mPattern = Pattern.compile(regex[i]);
            Matcher mMatcher = mPattern.matcher(call.request().url() + "");
            if (mMatcher.find()) {
                found = true;
                break;
            }
        }
        onFinally();
    }
}
