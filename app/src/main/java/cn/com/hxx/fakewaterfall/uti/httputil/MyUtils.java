package cn.com.hxx.fakewaterfall.uti.httputil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleProductData;
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


    //显示图片
    public static void drawTshitPic(RelativeLayout ll_cover_bg, RelativeLayout ll_cover_bg1, ImageView iv_cover, StyleProductData styleProductDataById, String imageUrl, String imageToshowInList, Context context) {
        final RelativeLayout ll_cover_bg_ = ll_cover_bg;
        final RelativeLayout ll_cover_bg1_ = ll_cover_bg1;
        final ImageView iv_cover_ = iv_cover;

        if (EmptyUtils.isNotEmpty(styleProductDataById)) {
            String imageToshow = null;
            if (EmptyUtils.isEmpty(imageUrl) && EmptyUtils.isEmpty(imageToshowInList)) {
                //当进入diy选择款式时进入
                imageToshow = styleProductDataById.getPreview_image_url();
            } else {
                //其他情况
                if (EmptyUtils.isEmpty(imageToshowInList)) {
                    imageToshow = styleProductDataById.getImageToshow(styleProductDataById.getImage_to_show_in_list());
                } else {
                    imageToshow = styleProductDataById.getImageToshow(imageToshowInList);
                }
            }
            if (EmptyUtils.isNotEmpty(imageToshow)) {
                //设置背景
                if (EmptyUtils.isNotEmpty(styleProductDataById.getShow_image3_url()) && EmptyUtils.isEmpty(styleProductDataById.getShow_image4_url_small())) {
                    //手机壳
                    Glide.with(context).load(imageToshow)
                            .into(new ViewTarget<View, GlideDrawable>(ll_cover_bg1) {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    this.view.setBackground(resource.getCurrent());
                                    ll_cover_bg_.setBackground(null);
                                }
                            });

                } else {
                    //非手机壳
                    Glide.with(context).load(imageToshow)
                            .into(new ViewTarget<View, GlideDrawable>(ll_cover_bg) {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    this.view.setBackground(resource.getCurrent());
                                    ll_cover_bg1_.setBackground(null);
                                }
                            });
                }
            }
            String offSetData;
            if (EmptyUtils.isNotEmpty(imageToshowInList)) {
                offSetData = styleProductDataById.getoffsetData(imageToshowInList);
            } else {
                offSetData = styleProductDataById.getoffsetData(styleProductDataById.getImage_to_show_in_list());
            }
            if (EmptyUtils.isNotEmpty(offSetData)) {    //diy款式选择时不进入
                final List<Float> offsetData = MyUtils.getOffsetData(offSetData);
                //设置图片
                if (EmptyUtils.isNotEmpty(imageUrl) && imageUrl.contains("http")) {
                    //加载网络图片
                    Glide.with(context).load(imageUrl).into(iv_cover);
                } else if (EmptyUtils.isNotEmpty(imageUrl)) {
                    //加载本地图片
                    Glide.with(context).load("file://" + imageUrl).into(iv_cover);
                }
                int width = iv_cover.getWidth();
                ViewTreeObserver viewTreeObserver = ll_cover_bg.getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //    ll_cover_bg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        float per = (float) (offsetData.get(2) * 0.4);    //宽度放缩比例
                        int height = ll_cover_bg_.getHeight();
                        int width = ll_cover_bg_.getWidth();
                        ViewGroup.LayoutParams layoutParams = iv_cover_.getLayoutParams();
                        layoutParams.width = (int) (width * per);
                        iv_cover_.setLayoutParams(layoutParams);

                        float v = (float) (width * (offsetData.get(0) - 0.5));
                        float v1 = (float) (height * (0.5 - offsetData.get(1)));
                        iv_cover_.setTranslationX(v);
                        iv_cover_.setTranslationY(v1);

                    }
                });
            }
        }
    }


    public static List<Float> getOffsetData(String offdata) {
        List<Float> list = new ArrayList<>();
        String[] splits = offdata.split("\\|");
        for (String split : splits) {
            if (split.contains(",")) {
                String[] splits1 = split.split(",");
                for (String split1 : splits1) {
                    list.add(Float.parseFloat(split1));
                }
            } else {
                list.add(Float.parseFloat(split));
            }
        }

        return list;
    }
}
