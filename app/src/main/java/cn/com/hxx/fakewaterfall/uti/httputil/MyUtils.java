package cn.com.hxx.fakewaterfall.uti.httputil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 2018/5/28.
 */

public class MyUtils {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (Retrofit.class) {
                //未初始化，则初始instance变量
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(MyConstantUtils.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(MyOKHttp.getClient())
                            .build();

                }
            }
        }
        return retrofit;
    }
}
