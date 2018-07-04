package cn.com.hxx.fakewaterfall.uti.httputil;

import java.util.List;

import cn.com.hxx.fakewaterfall.uti.httputil.data.CommodityData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.HomeData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.HomeNavButtonData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by apple on 2018/5/28.
 */

public interface MainWebInterface {


    //    home
    @GET("home")
    Call<HttpResult<HomeData>> getHomeData();

    //首页下面的一排按钮 HomeNavButtonData
    @GET("home_nav_buttons")
    Call<HttpResult<List<HomeNavButtonData>>> getHomeNavButtonData();

    //热门作品
    @GET("tshirts/explore")
    Call<HttpResult<List<CommodityData>>> getHotDesigns(@Query("page") int page, @Query("order") String order, @Query("count") int count);

    //
    @GET("styles")
    Call<HttpResult<List<StyleData>>> getStyles();

    //商品详情
    @GET("tshirts/{id}")
    Call<HttpResult<CommodityData>> getCommodityDetail(@Path("id") int id);
}
