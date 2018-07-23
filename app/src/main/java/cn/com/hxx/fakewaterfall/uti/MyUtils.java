package cn.com.hxx.fakewaterfall.uti;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.uti.bean.BigPack;
import cn.com.hxx.fakewaterfall.uti.httputil.EmptyUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.MyConstantUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.MyOKHttp;
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


    private static List<View> bigList = new ArrayList<>();
    private static List<View> smallList = new ArrayList<>();

    public static void saveItem(String type, View view){
        if (type.equalsIgnoreCase("big")){
            if (bigList.size() == 0){
                bigList.add(view);
            }
        }else if (type.equalsIgnoreCase("small")){
            if (smallList.size() == 0){
                smallList.add(view);
            }
        }
    }

    public static View getItem(String type){
        if (type.equalsIgnoreCase("big")){
            if (bigList.size() != 0){
                return bigList.remove(0);
            }
            return null;
        }else  if(type.equalsIgnoreCase("small")){
            if (smallList.size() != 0){
                return smallList.remove(0);
            }
            return null;
        }
        return null;
    }



    public static <T> boolean needLoadData(BaseQuickAdapter mQuickAdapter, BigPack result, int page, Context context, int empty_drawable, String empty_tips) {
        mQuickAdapter.addData(result);
        boolean hasContent = hasContent(page, result.getList().size());
        if (hasContent) {
            if (result.getList().size() < 10) {
                View footerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.usage_footer, null, false);
                mQuickAdapter.addFooterView(footerView);
                mQuickAdapter.setEnableLoadMore(false);
                mQuickAdapter.loadMoreEnd();
                return false;
            } else {
                mQuickAdapter.loadMoreComplete();
                return true;
            }
        } else {
            View inflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_empty_tips, null, false);
            inflate.setVisibility(View.VISIBLE);
            if (empty_drawable == 0) {
                ((ImageView) (inflate.findViewById(R.id.no_content_image))).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_consume));
            } else {
                ((ImageView) (inflate.findViewById(R.id.no_content_image))).setImageDrawable(ContextCompat.getDrawable(context, empty_drawable));
            }
            if (empty_tips == null) {
                ((TextView) (inflate.findViewById(R.id.no_content_text))).setText("还没有评论哦");
            } else {
                ((TextView) (inflate.findViewById(R.id.no_content_text))).setText(empty_tips);
            }
            mQuickAdapter.loadMoreComplete();
            mQuickAdapter.addFooterView(inflate);

            return false;
        }
    }


    public static boolean hasContent(int page, int size) {
        return !(page == 1 && size == 0);
    }

    public static void t(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void f(String str){
        Log.e("HXX", str);
    }
}
