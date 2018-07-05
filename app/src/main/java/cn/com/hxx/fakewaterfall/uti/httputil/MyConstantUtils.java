package cn.com.hxx.fakewaterfall.uti.httputil;

/**
 * Created by apple on 2018/5/28.
 */

public class MyConstantUtils {

    //商户号
    public static String RELEASE_BASE_URL = "http://woqi.me/api/";
    public static String BASE_URL = "http://staging.woqi.me/api/";

    public static String WEB_RELEASE_BASE_URL = "http://woqi.me";
    public static String WEB_BASE_URL = "http://staging.woqi.me";

    public static void init() {
        BASE_URL = RELEASE_BASE_URL;
        WEB_BASE_URL = WEB_RELEASE_BASE_URL;
    }
}
