package cn.com.hxx.fakewaterfall.Module.LruCache;

import android.accounts.NetworkErrorException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.hxx.fakewaterfall.Module.Http.Utils.AsynConnectionManager;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/8/2.
 */

public class MyLruCacheFragment extends BaseFragment implements View.OnClickListener{

    LruCache<String, Bitmap> mMemoryCache;//内存缓存
    private ImageView iv_pic1;
    private ImageView iv_pic2;
    private TextView tv_cache1;
    private TextView tv_cache2;

    final String imageUrl = "http://static.woqi.me/tshirt_style/preview_image/107/zhanshi-diy.jpg?imageView2/2/w/250/h/250/format/png/q/85";

    @Override
    public int getContentView() {
        return R.layout.fragment_mylrucache;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initLruCache();
    }
    private void initView() {
        getView().findViewById(R.id.btn_getpic1).setOnClickListener(this);
        getView().findViewById(R.id.btn_delpic1).setOnClickListener(this);
        getView().findViewById(R.id.btn_getpic2).setOnClickListener(this);
        getView().findViewById(R.id.btn_delpic2).setOnClickListener(this);
        iv_pic1 = getView().findViewById(R.id.iv_pic1);
        iv_pic2 = getView().findViewById(R.id.iv_pic2);
        tv_cache1 = getView().findViewById(R.id.tv_cache1);
        tv_cache2 = getView().findViewById(R.id.tv_cache2);
    }


    private void initLruCache() {
        //获取程序最大内存
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        //1/8作为内存缓存的大小
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //以每张图片的大小作为size
                return value.getByteCount() / 1024;
            }
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_getpic1:
                final Bitmap bitmap = mMemoryCache.get(imageUrl);
                if (bitmap == null){
                    AsynConnectionManager.downLoadPic(imageUrl, new AsynConnectionManager.CallBack<Bitmap>() {
                        @Override
                        public void onSucces(Bitmap reponse) {
                            iv_pic1.setImageBitmap(reponse);
                            mMemoryCache.put(imageUrl, reponse
                            );
                        }
                    });

                }else {
                    iv_pic1.setImageBitmap(bitmap);
                    tv_cache1.setText(mMemoryCache.size() + "");
                }

                break;
            case R.id.btn_delpic1:
                mMemoryCache.remove(imageUrl);
                tv_cache1.setText(mMemoryCache.size() + "");
                iv_pic1.setImageBitmap(null);
                break;
            case R.id.btn_getpic2:

                break;
            case R.id.btn_delpic2:

                break;
        }
    }
}
