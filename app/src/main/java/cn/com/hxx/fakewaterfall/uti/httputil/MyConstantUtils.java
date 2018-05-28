package cn.com.hxx.fakewaterfall.uti.httputil;

/**
 * Created by apple on 2018/5/28.
 */

public class MyConstantUtils {

    public static final String WECHAT_APPID = "wx3b0c340c3c557dab";
    public static final String API_KEY = "9dfac97d15426258d3ee4df6cee31418";
    //商户号
    public static final String MCH_ID = "1233434902";
    public static String WECHAT_SECRET = "e775a956489424f1fdfd03c638c06e50";
    public static String RELEASE_BASE_URL = "http://woqi.me/api/";
    public static String BASE_URL = "http://staging.woqi.me/api/";
    public static String REGISTER_URL = BASE_URL + "pages/terms";
    public static String ABOUT_O2READ_URL = BASE_URL + "pages/about_o2read";
    public static String SERVICE_URL = BASE_URL + "pages/term_of_service";
    public static String WECHAT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String WECHAT_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    public static String LOGIN_PROBLEM_URL = BASE_URL + "sessions/getpassindex";
    public static String COMMEN_PROBLEM = BASE_URL + "o2bas/problems_see";
    public static String OWNER_RULES = BASE_URL + "o2bas/o2ba_rules";
    public static String BAR_DETAILS = BASE_URL + "o2bas/about_o2ba";
    public static String BAR_INTRO = BASE_URL + "o2bas/description";
    public static String TING_INVOLVE = BASE_URL + "pages/ting_involve";

    public static String NOTIFY_URL = BASE_URL + "payments/alipay/notify";

    public static String WEB_RELEASE_BASE_URL = "http://woqi.me";
    public static String WEB_BASE_URL = "http://staging.woqi.me";

    public static void init() {
        BASE_URL = RELEASE_BASE_URL;
        WEB_BASE_URL = WEB_RELEASE_BASE_URL;
        REGISTER_URL = BASE_URL + "pages/terms";
        ABOUT_O2READ_URL = BASE_URL + "pages/about_o2read";
        SERVICE_URL = BASE_URL + "pages/term_of_service";
        LOGIN_PROBLEM_URL = BASE_URL + "sessions/getpassindex";

        COMMEN_PROBLEM = BASE_URL + "o2bas/problems_see";
        OWNER_RULES = BASE_URL + "o2bas/o2ba_rules";
        BAR_DETAILS = BASE_URL + "o2bas/about_o2ba";
        BAR_INTRO = BASE_URL + "o2bas/description";
        TING_INVOLVE = BASE_URL + "pages/ting_involve";
        NOTIFY_URL = BASE_URL + "payments/alipay/notify";
    }
}
