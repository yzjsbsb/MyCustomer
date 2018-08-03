package cn.com.hxx.fakewaterfall.uti;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cn.com.hxx.fakewaterfall.MainActivity;
import cn.com.hxx.fakewaterfall.MyAnotation.MyAnotation;
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


    public static void t(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void f(String str){
        Log.e("HXX", str);
    }

    public static void generateButton(final ViewGroup ll_container, final Context context, String methodTag, @Nullable final int fragmentContainer, View viewDisappear){
        //利用反射获取所在类中"start"开头的方法
        List<Method> methodList = new ArrayList<>();
        for (Method method : context.getClass().getMethods()){
            if (method.getName().startsWith(methodTag)){
                methodList.add(method);
            }
        }
        //对methodList进行排序,用以确定button生成对顺序
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            methodList.sort(new Comparator<Method>() {
                @Override
                public int compare(Method o1, Method o2) {
                    MyAnotation annotation1 = o1.getAnnotation(MyAnotation.class);
                    MyAnotation annotation2 = o2.getAnnotation(MyAnotation.class);
                    return annotation1.order() > annotation2.order()? 1 : 0;
                }
            });
        }
        final View viewDisappear1 = viewDisappear;
        //生成button并利用反射添加button点击效果
        for (final Method method : methodList){
            Button button = new Button(context);
            button.setText(method.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Object invoke = method.invoke(context);
                        if (invoke != null && invoke instanceof Fragment){
                            Fragment fragment = (Fragment)invoke;
                            FragmentManager supportFragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                            fragmentTransaction.add(fragmentContainer, fragment);
                            fragmentTransaction.addToBackStack("sdfasdf");   //按back时，不是activity销毁，而是fragment销毁
                            fragmentTransaction.commit();
                            if (viewDisappear1 != null) viewDisappear1.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ll_container.addView(button);
        }
    }


    public static String getMD5String(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            ///sdcard/Android/data/application package/cache
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/application package/cache
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
