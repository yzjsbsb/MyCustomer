package cn.com.hxx.fakewaterfall.uti.httputil;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by apple on 2018/5/28.
 */

public class MyHttpUtils {

    public static MainWebInterface mainWebService;
    public static CommonWebInterface commonWebService;
    public static final int OK = 200;
    public static ProgressDialog progressDialog;

    public static MainWebInterface getMainWebService() {
        if (mainWebService == null) {
            mainWebService = MyUtils.getRetrofit().create(MainWebInterface.class);
        }
        return mainWebService;
    }

    public static CommonWebInterface getCommonWebService() {
        if (commonWebService == null) {
            commonWebService = MyUtils.getRetrofit().create(CommonWebInterface.class);
        }
        return commonWebService;
    }

    public static void hideLoading() {
        if (progressDialog != null) {
            try {
                progressDialog.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createLoading(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    public static void showLoading(Context context, String tips) {
        hideLoading();
        createLoading(context);
        progressDialog.setMessage(tips);
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLoading(Context context) {
        showLoading(context, "处理中");
    }
}
